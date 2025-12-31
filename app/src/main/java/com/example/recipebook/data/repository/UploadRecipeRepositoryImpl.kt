package com.example.recipebook.data.repository

import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.NewRecipe
import com.example.recipebook.domain.model.RecipeStepDraft
import com.example.recipebook.domain.repository.UploadRecipeRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadRecipeRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val imageCompressorImpl: ImageCompressorImpl
) : UploadRecipeRepository {

    override suspend fun uploadStepImages(
        recipeId: String,
        steps: List<RecipeStepDraft>
    ): Map<String, String> = coroutineScope {
        steps
            .filter { it.imageSource != null }
            .map { step ->
                async(Dispatchers.IO) {
                    step.id to uploadStepImage(
                        recipeId = recipeId,
                        stepId = step.id,
                        imageBytes = imageCompressorImpl.compress(step.imageSource)
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

    override suspend fun saveRecipe(recipe: NewRecipe) {
        firestore
            .collection("recipes")
            .document(recipe.id)
            .set(recipe)
            .await()
    }

    override suspend fun uploadRecipeImage(recipeId: String, imageSource: String?): String {
        val ref = firebaseStorage.reference
            .child("recipes")
            .child(recipeId)
            .child("cover")
            .child("recipe_cover.jpg")
        ref.putBytes(imageCompressorImpl.compress(imageSource)).await()
        return ref.downloadUrl.await().toString()
    }
}