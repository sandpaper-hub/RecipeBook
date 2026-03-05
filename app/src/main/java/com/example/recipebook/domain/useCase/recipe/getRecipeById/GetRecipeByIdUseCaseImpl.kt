package com.example.recipebook.domain.useCase.recipe.getRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeByIdUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
) : GetRecipeByIdUseCase {
    override suspend fun execute(recipeId: String): Recipe =
        recipesRepository.getRecipeById(recipeId)
}