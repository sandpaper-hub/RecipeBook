package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.NewRecipe
import com.example.recipebook.domain.model.RecipeStepDraft

interface UploadRecipeRepository {
    suspend fun uploadStepImages(recipeId: String, steps: List<RecipeStepDraft>): Map<String, String>
    suspend fun uploadStepImage(recipeId: String, stepId: String, imageBytes: ByteArray): String
    suspend fun saveRecipe(recipe: NewRecipe)
    suspend fun uploadRecipeImage(recipeId: String, imageSource: String?): String
}