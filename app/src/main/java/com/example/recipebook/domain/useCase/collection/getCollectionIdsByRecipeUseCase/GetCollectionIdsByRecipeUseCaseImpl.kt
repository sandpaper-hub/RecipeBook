package com.example.recipebook.domain.useCase.collection.getCollectionIdsByRecipeUseCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetCollectionIdsByRecipeUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(recipeId: String): List<String> =
        collectionsRepository.getCollectionIdsByRecipe(recipeId)
}