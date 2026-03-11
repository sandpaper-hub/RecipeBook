package com.example.recipebook.domain.useCase.recipe.getRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeByIdFlowUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
) : GetRecipeByIdFlowUseCase {
    override suspend fun execute(recipeId: String): Flow<Recipe> {
        return recipesRepository.getRecipeByIdFlow(recipeId)
    }
}