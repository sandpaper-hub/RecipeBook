package com.example.recipebook.domain.interactor.recipes.getRecipeSteps

import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeStepsUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
) : GetRecipeStepsUseCase{
    override suspend fun execute(recipeId: String): List<Step> =
        recipesRepository.getRecipeSteps(recipeId)
}