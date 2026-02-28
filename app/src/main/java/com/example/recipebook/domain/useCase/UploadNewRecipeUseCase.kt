package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class UploadNewRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(newRecipe: UploadRecipe, steps: List<UploadRecipeStep>) {
        recipesRepository.saveRecipe(newRecipe, steps)
    }
}