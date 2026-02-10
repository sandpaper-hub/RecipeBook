package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    suspend fun createRandomId(): String
    suspend fun uploadStepImages(
        recipeId: String,
        steps: List<NewRecipeStepDraft>
    ): Map<String, String>

    suspend fun uploadStepImage(recipeId: String, stepId: String, imageBytes: ByteArray): String
    suspend fun saveRecipe(newRecipe: NewRecipe, recipeSteps: List<NewRecipeStep>)
    suspend fun uploadRecipeImage(recipeId: String, imageSource: String): String
    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
    suspend fun getRecipeById(recipeId: String): Recipe
    suspend fun getRecipeSteps(recipeId: String): List<Step>

    suspend fun deleteRecipe(recipeId: String)
}