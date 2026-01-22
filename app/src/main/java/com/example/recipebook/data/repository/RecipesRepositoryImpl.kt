package com.example.recipebook.data.repository

import com.example.recipebook.data.dto.RecipeDto
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.data.mapper.toDto
import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import com.example.recipebook.domain.repository.RecipesRepository
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
    private val imageCompressorImpl: ImageCompressorImpl
) : RecipesRepository {

    override suspend fun uploadStepImages(
        recipeId: String,
        steps: List<RecipeStepDraft>
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

    override suspend fun saveRecipe(recipe: Recipe) {
        firestore
            .collection("recipes")
            .document(recipe.id)
            .set(recipe.toDto())
            .await()
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

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> = callbackFlow{
        val query = firestore.collection("recipes")
            .whereEqualTo("authorId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { snapshot, error ->
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
}