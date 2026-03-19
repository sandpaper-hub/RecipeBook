package com.example.recipebook.domain.useCase.recipe

import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class UpdateRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(
        recipe: UploadRecipe,
        deleteSteps: List<UploadRecipeStep>,
        updateSteps: List<UploadRecipeStep>,
        addSteps: List<UploadRecipeStep>
    ) {
        recipesRepository.updateRecipe(
            recipe = recipe,
            deleteSteps = deleteSteps,
            updateSteps = updateSteps,
            addSteps = addSteps
        )
    }
}