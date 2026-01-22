package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun uploadStepImages(recipeId: String, steps: List<RecipeStepDraft>): Map<String, String>
    suspend fun uploadStepImage(recipeId: String, stepId: String, imageBytes: ByteArray): String
    suspend fun saveRecipe(recipe: Recipe)
    suspend fun uploadRecipeImage(recipeId: String, imageSource: String): String
    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
}