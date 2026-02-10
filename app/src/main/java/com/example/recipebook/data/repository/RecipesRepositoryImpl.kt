package com.example.recipebook.data.repository

import com.example.recipebook.data.dto.getRecipe.RecipeDto
import com.example.recipebook.data.dto.getRecipe.StepDto
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.data.mapper.toDto
import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.repository.RecipesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        steps: List<NewRecipeStepDraft>
    ): Map<String, String> = coroutineScope {
        steps
            .mapNotNull { step ->
                val source = step.imageSource ?: return@mapNotNull null

                async(Dispatchers.IO) {
                    step.id to uploadStepImage(
                        recipeId = recipeId,
                        stepId = step.id,
                        imageBytes = imageCompressorImpl.compress(source)
                    )
                }
            }
            .awaitAll()
            .toMap()
    }

    override suspend fun uploadStepImage(
        recipeId: String,
        stepId: String,
        imageBytes: ByteArray
    ): String {
        val ref = firebaseStorage.reference
            .child("recipes")
            .child(recipeId)
            .child("steps")
            .child("$stepId.jpg")

        ref.putBytes(imageBytes).await()
        return ref.downloadUrl.await().toString()
    }

    override suspend fun saveRecipe(newRecipe: NewRecipe, recipeSteps: List<NewRecipeStep>) {
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
}