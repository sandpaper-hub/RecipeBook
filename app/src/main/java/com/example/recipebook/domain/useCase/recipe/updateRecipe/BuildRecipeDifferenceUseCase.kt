package com.example.recipebook.domain.useCase.recipe.updateRecipe

import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.recipe.step.ImageSourceType
import com.example.recipebook.domain.model.recipe.update.RecipeDifference
import javax.inject.Inject

class BuildRecipeDifferenceUseCase @Inject constructor() {
    fun execute(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ): RecipeDifference {
        val originalIds = originalRecipe.steps.map { it.id }.toSet()
        val editedIds = editedRecipe.steps.map { it.id }.toSet()

        val delete = originalRecipe.steps.filter { it.id !in editedIds }.map { deleteStep ->
            EditStep(
                id = deleteStep.id,
                imageSourceType = ImageSourceType.None
            )
        }
        val add = editedRecipe.steps.filter { it.id !in originalIds }
        val update = editedRecipe.steps.filter { it.id in originalIds }

        return RecipeDifference(
            stepsToDelete = delete,
            stepsToAdd = add,
            stepsToUpdate = update
        )
    }
}