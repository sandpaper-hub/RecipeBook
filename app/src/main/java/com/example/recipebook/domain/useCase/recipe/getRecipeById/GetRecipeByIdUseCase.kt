package com.example.recipebook.domain.useCase.recipe.getRecipeById

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe

interface GetRecipeByIdUseCase {
    suspend fun execute(recipeId: String): Recipe
}