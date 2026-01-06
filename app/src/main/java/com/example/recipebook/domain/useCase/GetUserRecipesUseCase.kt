package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRecipesUseCase @Inject constructor(
    private val userProfileRepository: ProfileRepository
) {
    fun execute(userId: String): Flow<List<Recipe>> =
        userProfileRepository.observeUserRecipes(userId)
}