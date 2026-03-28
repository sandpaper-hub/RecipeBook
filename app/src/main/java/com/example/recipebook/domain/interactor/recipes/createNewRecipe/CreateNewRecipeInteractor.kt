package com.example.recipebook.domain.interactor.recipes.createNewRecipe

import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft

interface CreateNewRecipeInteractor {
    suspend fun invoke(
        recipeName: String,
        recipeDescription: String,
        recipeNewTimeEstimation: NewTimeEstimation,
        recipeImageSource: ImageSourceType,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<UploadRecipeStepDraft>
    )
}