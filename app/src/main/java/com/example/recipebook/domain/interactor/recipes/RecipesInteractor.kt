package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStep
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import kotlinx.coroutines.flow.Flow

interface RecipesInteractor {

    suspend fun createRandomId(): String
    suspend fun uploadNewRecipe(
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<RecipeIngredient>,
        steps: List<RecipeStepDraft>
    )

    suspend fun buildRecipeSteps(
        recipeId: String,
        recipeStepDrafts: List<RecipeStepDraft>
    ): List<RecipeStep>

    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
    fun getUserIdFlow(): Flow<String?>
}