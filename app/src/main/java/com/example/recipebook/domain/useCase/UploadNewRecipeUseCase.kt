package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.repository.UploadRecipeRepository
import javax.inject.Inject

class UploadNewRecipeUseCase @Inject constructor(
    private val uploadRecipeRepository: UploadRecipeRepository
) {
    suspend fun execute(recipe: Recipe) {
        uploadRecipeRepository.saveRecipe(recipe)
    }
}