package com.example.recipebook.domain.useCase.recipe

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
     suspend fun execute(recipeId: String) {
        recipesRepository.deleteRecipe(recipeId)
    }
}