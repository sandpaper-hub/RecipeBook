package com.example.recipebook.domain.repository

interface DeleteImageRepository {
    suspend fun deleteStepImageByPath(recipeId: String, stepId: String)
    suspend fun deleteRecipeImageByPath(recipeId: String)
}