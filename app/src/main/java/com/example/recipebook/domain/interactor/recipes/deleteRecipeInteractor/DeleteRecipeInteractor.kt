package com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor

interface DeleteRecipeInteractor {
    suspend fun invoke(recipeId: String)
}