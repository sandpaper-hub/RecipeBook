package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import com.example.recipebook.domain.repository.UploadRecipeRepository
import javax.inject.Inject

class GetStepImagesUrlUseCase @Inject constructor(
    private val uploadRecipeRepository: UploadRecipeRepository,
) {
    suspend fun execute(recipeId: String, recipeSteps: List<RecipeStepDraft>): Map<String, String> =
        uploadRecipeRepository.uploadStepImages(recipeId = recipeId, steps = recipeSteps)
}