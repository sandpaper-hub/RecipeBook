package com.example.recipebook.domain.useCase.recipe.deleteRecipe

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class DeleteRecipeUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
): DeleteRecipeUseCase {
    override suspend fun execute(recipeId: String) {
        recipesRepository.deleteRecipe(recipeId)
    }
}