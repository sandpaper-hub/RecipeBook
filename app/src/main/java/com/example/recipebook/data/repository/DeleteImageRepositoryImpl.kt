package com.example.recipebook.data.repository

import com.example.recipebook.domain.repository.DeleteImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteImageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : DeleteImageRepository {


    override suspend fun deleteStepImageByPath(
        recipeId: String, stepId: String
    ) {
        val storageRef = firebaseStorage.reference
            .child("recipes/$recipeId/steps/$stepId.jpg")
        storageRef.delete().await()
    }

    override suspend fun deleteRecipeImageByPath(recipeId: String) {
        val storageRef = firebaseStorage.reference
            .child("recipes/$recipeId/cover/recipe_cover.jpg")
        storageRef.delete().await()
    }
}