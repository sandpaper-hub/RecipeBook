package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import kotlinx.coroutines.flow.Flow

interface RecipesInteractor {
    suspend fun getRecipeById(recipeId: String): Recipe

    suspend fun createRandomId(): String
    suspend fun uploadNewRecipe(
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<NewRecipeStepDraft>
    )

    suspend fun buildRecipeSteps(
        recipeId: String,
        newRecipeStepDrafts: List<NewRecipeStepDraft>
    ): List<NewRecipeStep>

    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
    fun getUserIdFlow(): Flow<String?>
    suspend fun getRecipeSteps(recipeId: String): List<Step>
}