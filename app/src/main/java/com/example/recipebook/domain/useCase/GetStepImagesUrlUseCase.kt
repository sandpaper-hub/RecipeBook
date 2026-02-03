package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class GetStepImagesUrlUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository,
) {
    suspend fun execute(recipeId: String, recipeSteps: List<NewRecipeStepDraft>): Map<String, String> =
        recipesRepository.uploadStepImages(recipeId = recipeId, steps = recipeSteps)
}