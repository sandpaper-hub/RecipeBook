package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe

interface UpdateRecipeInteractor {
    suspend fun updateRecipe(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    )
}