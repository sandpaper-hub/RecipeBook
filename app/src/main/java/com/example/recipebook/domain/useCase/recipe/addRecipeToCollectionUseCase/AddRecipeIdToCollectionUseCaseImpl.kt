package com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class AddRecipeIdToCollectionUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
): AddRecipeToCollectionUseCase {
    override suspend fun execute(recipeId: String, collectionId: String) {
        collectionsRepository.toggleRecipeInCollection(
            collectionId = collectionId, recipeId = recipeId, add = true
        )
    }
}