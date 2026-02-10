package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeStepsUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeId: String): List<Step> =
        recipesRepository.getRecipeSteps(recipeId)
}