package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class AddRecipeToCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(collectionId: String, recipeId: String) {
        collectionsRepository.addRecipeToCollection(
            collectionId = collectionId,
            recipeId = recipeId
        )
    }
}