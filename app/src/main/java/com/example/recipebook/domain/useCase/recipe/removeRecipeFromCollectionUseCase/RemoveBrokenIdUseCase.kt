package com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase

interface RemoveBrokenIdUseCase {
    suspend fun execute(collectionId: String, recipeId: String)
}