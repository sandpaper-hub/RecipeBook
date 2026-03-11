package com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class RemoveBrokenIdUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : RemoveBrokenIdUseCase {
    override suspend fun execute(collectionId: String, recipeId: String) {
        collectionsRepository.toggleRecipeInCollection(
            collectionId = collectionId, recipeId = recipeId, add = false
        )
    }
}