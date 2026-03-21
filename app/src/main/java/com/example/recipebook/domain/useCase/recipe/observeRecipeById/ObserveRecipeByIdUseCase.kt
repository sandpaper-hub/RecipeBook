package com.example.recipebook.domain.useCase.recipe.observeRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import kotlinx.coroutines.flow.Flow

interface ObserveRecipeByIdUseCase {
    suspend fun execute(recipeId: String): Flow<Recipe>
}