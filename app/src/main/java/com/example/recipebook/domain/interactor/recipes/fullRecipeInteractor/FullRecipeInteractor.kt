package com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor

import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe

interface FullRecipeInteractor {
    suspend fun getFullRecipe(recipeId: String): FullRecipe
}