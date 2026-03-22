package com.example.recipebook.data.repository

import android.util.Log
import com.example.recipebook.data.dto.getRecipe.RecipeDto
import com.example.recipebook.data.dto.getRecipe.StepDto
import com.example.recipebook.data.mapper.toDataError
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.data.mapper.toDto
import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.AppResult
import com.example.recipebook.domain.model.DataError
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.repository.RecipesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val imageCompressorImpl: ImageCompressorImpl
) : RecipesRepository {

    private val userId get() = auth.currentUser!!.uid

    override suspend fun createRandomId(): String {
        val document = firestore
            .collection("random")
            .document()
        return document.id
    }

    override suspend fun uploadStepImages(
        recipeId: String,
        steps: List<UploadRecipeStepDraft>
    ): Map<String, String> = coroutineScope {
        steps
            .mapNotNull { step ->
                val source = step.imageSource ?: return@mapNotNull null

                async(Dispatchers.IO) {
                    step.id to uploadStepImage(
                        recipeId = recipeId,
                        stepId = step.id,
                        source = source
                    )
                }
            }
            .awaitAll()
            .toMap()
    }

    override suspend fun uploadStepImage(
        recipeId: String,
        stepId: String,
        source: String
    ): String {
        val imageBytes = imageCompressorImpl.compress(source)
        val ref = firebaseStorage.reference
            .child("recipes")
            .child(recipeId)
            .child("steps")
            .child("$stepId.jpg")

        ref.putBytes(imageBytes).await()
        return ref.downloadUrl.await().toString()
    }

    override suspend fun saveRecipe(newRecipe: UploadRecipe, recipeSteps: List<UploadRecipeStep>) {
        val recipeReference = firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(newRecipe.id)

        firestore.runBatch { batch ->
            batch.set(
                recipeReference,
                newRecipe.toDto()
            )

            recipeSteps.forEach { step ->
                val stepReference = recipeReference
                    .collection("steps")
                    .document(step.id)

                batch.set(
                    stepReference,
                    step.toDto()
                )
            }
        }
    }

    override suspend fun uploadRecipeImage(recipeId: String, imageSource: String): String {
        val ref = firebaseStorage.reference
            .child("recipes")
            .child(recipeId)
            .child("cover")
            .child("recipe_cover.jpg")
        ref.putBytes(imageCompressorImpl.compress(imageSource)).await()
        return ref.downloadUrl.await().toString()
    }

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> = callbackFlow {
        val listener = firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val recipes = snapshot
                    ?.toObjects(RecipeDto::class.java)
                    ?.map { it.toDomain() }
                    ?: emptyList()
                trySend(recipes)
            }

        awaitClose {
            listener.remove()
        }
    }

    override fun getRecipeByIdFlow(recipeId: String): Flow<Recipe> = callbackFlow {
        val listener = firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(recipeId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val recipe = snapshot?.toObject(RecipeDto::class.java)?.toDomain()
                    ?: return@addSnapshotListener
                trySend(recipe)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun getRecipeById(recipeId: String): Recipe {
        return firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(recipeId)
            .get()
            .await()
            .toObject(RecipeDto::class.java)?.toDomain()
            ?: throw IllegalStateException("Recipe not found")
    }

    override suspend fun getRecipeSteps(recipeId: String): List<Step> {
        return firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(recipeId)
            .collection("steps")
            .orderBy("order", Query.Direction.ASCENDING)
            .get()
            .await()
            .toObjects(StepDto::class.java).map {
                it.toDomain()
            }
    }

    override suspend fun deleteRecipe(recipeId: String) {
        firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(recipeId)
            .delete()
            .await()
    }

    override suspend fun getRecipesByIds(
        recipesIds: List<String>
    ): List<Recipe> {
        if (recipesIds.isEmpty()) return emptyList()

        val chunks = recipesIds.chunked(10)
        val result = mutableListOf<Recipe>()

        for (chunk in chunks) {
            val snapshot = firestore
                .collection("users")
                .document(userId)
                .collection("recipes")
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()
            result += snapshot.toObjects(RecipeDto::class.java)
                .map { it.toDomain() }
        }
        return result
    }

    override suspend fun updateRecipe(
        recipe: UploadRecipe,
        deleteSteps: List<UploadRecipeStep>,
        updateSteps: List<UploadRecipeStep>,
        addSteps: List<UploadRecipeStep>
    ) {
        val batch = firestore.batch()
        val recipeRef = firestore
            .collection("users")
            .document(userId)
            .collection("recipes")
            .document(recipe.id)

        batch.set(recipeRef, recipe.toDto())

        deleteSteps.forEach { step ->
            val stepRef = recipeRef
                .collection("steps")
                .document(step.id)
            batch.delete(stepRef)
        }

        updateSteps.forEach { step ->
            val stepRef = recipeRef
                .collection("steps")
                .document(step.id)

            batch.set(stepRef, step.toDto())
        }

        addSteps.forEach { step ->
            val stepRef = recipeRef
                .collection("steps")
                .document(step.id)

            batch.set(stepRef, step.toDto())
        }

        batch.commit().await()
    }

    override suspend fun searchRecipe(query: String): AppResult<List<Recipe>> {
        return try {
            val recipes = firestore.collection("users")
                .document(userId)
                .collection("recipes")
                .whereGreaterThanOrEqualTo("nameLowerCase", query.lowercase())
                .whereLessThan("nameLowerCase", "${query.lowercase()}\uF7FF")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .await()
                .toObjects(RecipeDto::class.java).map { it.toDomain() }
            Log.d("UISTATETEST", recipes.toString())
            AppResult.Success(recipes)
        } catch (exception: FirebaseFirestoreException) {
            Log.d("UISTATETEST", exception.toString())
            AppResult.Error(exception.toDataError())
        } catch (exception: Exception) {
            Log.d("UISTATETEST", exception.toString())
            AppResult.Error(DataError.Unknown)
        }
    }
}