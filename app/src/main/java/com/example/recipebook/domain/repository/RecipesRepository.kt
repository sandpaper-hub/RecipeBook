package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    suspend fun createRandomId(): String
    suspend fun uploadStepImages(
        recipeId: String,
        steps: List<UploadRecipeStepDraft>
    ): Map<String, String>

    suspend fun uploadStepImage(recipeId: String, stepId: String, source: String): String
    suspend fun saveRecipe(newRecipe: UploadRecipe, recipeSteps: List<UploadRecipeStep>)
    suspend fun uploadRecipeImage(recipeId: String, imageSource: String): String
    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
    suspend fun getRecipeById(recipeId: String): Recipe
    suspend fun getRecipeSteps(recipeId: String): List<Step>
    suspend fun deleteRecipe(recipeId: String)
    suspend fun getRecipesByIds(recipesIds: List<String>): List<Recipe>
    suspend fun updateRecipe(
        recipe: UploadRecipe,
        deleteSteps: List<UploadRecipeStep>,
        updateSteps: List<UploadRecipeStep>,
        addSteps: List<UploadRecipeStep>
    )
}