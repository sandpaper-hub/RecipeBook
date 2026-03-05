package com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase

interface RemoveRecipeFromCollectionUseCase {
    suspend fun execute(collectionId: String, recipeId: String)
}