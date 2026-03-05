package com.example.recipebook.domain.useCase.recipe.deleteRecipe

interface DeleteRecipeUseCase {
    suspend fun execute(recipeId: String)
}