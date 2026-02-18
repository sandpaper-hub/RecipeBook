package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipesByIdsUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeIds: List<String>): List<Recipe> {
        return recipesRepository.getRecipesByIds(recipeIds)
    }
}