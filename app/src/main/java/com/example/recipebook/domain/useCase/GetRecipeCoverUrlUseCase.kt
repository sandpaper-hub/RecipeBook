package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeCoverUrlUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeId: String, imageSource: String?): String? =
        if (imageSource == null) null
        else recipesRepository.uploadRecipeImage(recipeId, imageSource)
}