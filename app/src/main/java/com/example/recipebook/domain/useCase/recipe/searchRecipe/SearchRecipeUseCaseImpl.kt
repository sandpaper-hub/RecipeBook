package com.example.recipebook.domain.useCase.recipe.searchRecipe

import com.example.recipebook.domain.model.AppResult
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class SearchRecipeUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
): SearchRecipeUseCase {
    override suspend fun execute(query: String): AppResult<List<Recipe>> {
        return recipesRepository.searchRecipe(query)
    }
}