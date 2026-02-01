package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(recipeId: String): Recipe = recipesRepository.getRecipeById(recipeId)
}