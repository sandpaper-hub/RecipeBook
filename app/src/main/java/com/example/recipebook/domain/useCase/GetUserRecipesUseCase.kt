package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRecipesUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    fun execute(userId: String): Flow<List<Recipe>> =
        recipesRepository.observeUserRecipes(userId)
}