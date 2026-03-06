package com.example.recipebook.domain.useCase.recipe.getRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeId: String): Recipe{
        return recipesRepository.getRecipeById(recipeId)
    }
}