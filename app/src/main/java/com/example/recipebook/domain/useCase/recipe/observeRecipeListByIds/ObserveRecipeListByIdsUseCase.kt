package com.example.recipebook.domain.useCase.recipe.observeRecipeListByIds

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import kotlinx.coroutines.flow.Flow

interface ObserveRecipeListByIdsUseCase {
    fun execute(userId: String): Flow<List<Recipe>>
}