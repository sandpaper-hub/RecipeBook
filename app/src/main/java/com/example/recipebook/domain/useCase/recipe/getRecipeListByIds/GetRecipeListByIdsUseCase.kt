package com.example.recipebook.domain.useCase.recipe.getRecipeListByIds

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe

interface GetRecipeListByIdsUseCase {
    suspend fun execute(recipeIds: List<String>): List<Recipe>
}