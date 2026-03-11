package com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase

interface AddRecipeToCollectionUseCase {
    suspend fun execute(recipeId: String, collectionId: String)
}