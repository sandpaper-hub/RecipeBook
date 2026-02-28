package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetStepImageUrlUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeId: String, stepId: String, source: String): String {
        return recipesRepository.uploadStepImage(recipeId, stepId, source)
    }
}