package com.example.recipebook.domain.useCase.recipe.getRecipeListByIds

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeListByIdsUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
): GetRecipeListByIdsUseCase {
    override suspend fun execute(recipeIds: List<String>): List<Recipe> {
        return recipesRepository.getRecipesByIds(recipeIds)
    }
}