package com.example.recipebook.domain.interactor.uploadRecipe

import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStep
import com.example.recipebook.domain.model.recipe.RecipeStepDraft

interface UploadRecipeInteractor {
    suspend fun uploadNewRecipe(
        recipeId: String,
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
}