package com.example.recipebook.domain.useCase.recipe.searchRecipe

import com.example.recipebook.domain.model.AppResult
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe

interface SearchRecipeUseCase {
    suspend fun execute(query: String): AppResult<List<Recipe>>
}