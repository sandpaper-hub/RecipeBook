package com.example.recipebook.domain.useCase.recipe.observeRecipeListByIds

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRecipeListByIdsUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
): ObserveRecipeListByIdsUseCase {
    override fun execute(userId: String): Flow<List<Recipe>> =
        recipesRepository.observeUserRecipes(userId)
}