package com.example.recipebook.domain.useCase.recipe.getRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import kotlinx.coroutines.flow.Flow

interface GetRecipeByIdFlowUseCase {
    suspend fun execute(recipeId: String): Flow<Recipe>
}