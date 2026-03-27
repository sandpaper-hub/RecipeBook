package com.example.recipebook.domain.interactor.recipes.updateRecipeInteractor

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.service.StepImageProcessor
import com.example.recipebook.domain.useCase.recipe.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.recipe.UpdateRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.deleteRecipeImage.DeleteRecipeImageUseCase
import com.example.recipebook.domain.useCase.recipe.BuildRecipeDifferenceUseCase
import javax.inject.Inject

class UpdateRecipeInteractorImpl @Inject constructor(
    private val buildRecipeDifferenceUseCase: BuildRecipeDifferenceUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteRecipeImageUseCase: DeleteRecipeImageUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val imageProcessor: StepImageProcessor
) : UpdateRecipeInteractor {
    override suspend fun updateRecipe(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ) {
        val recipeImageSource: String? = updateRecipeImageSource(editedRecipe, originalRecipe)
        val recipe = UploadRecipe(
            id = editedRecipe.id,
            authorId = editedRecipe.authorId,
            recipeName = editedRecipe.recipeName,
            recipeDescription = editedRecipe.recipeDescription,
            recipeNewTimeEstimation = NewTimeEstimation(
                hour = editedRecipe.recipeTimeEstimation.hour,
                minute = editedRecipe.recipeTimeEstimation.minute
            ),
            imageUrl = recipeImageSource,
            category = editedRecipe.category.name,
            ingredients = editedRecipe.ingredients.map {
                NewRecipeIngredient(
                    id = it.id,
                    value = it.value,
                    amount = it.amount,
                    measure = it.measure.name
                )
            }
        )

        val difference = buildRecipeDifferenceUseCase.execute(
            editedRecipe = editedRecipe,
            originalRecipe = originalRecipe
        )

        val originalMap = originalRecipe.steps.associateBy { it.id }

        val stepsToUpdate = difference.stepsToUpdate.mapIndexed { index, step ->
            UploadRecipeStep(
                id = step.id,
                title = step.title,
                order = index,
                description = step.description,
                imageUrl = imageProcessor.resolveImageUrl(
                    recipeId = editedRecipe.id,
                    step = step,
                    oldStep = originalMap[step.id]
                )
            )
        }

        val stepsToAdd = difference.stepsToAdd.mapIndexed { index, step ->
            UploadRecipeStep(
                id = step.id,
                title = step.title,
                order = stepsToUpdate.size + index,
                description = step.description,
                imageUrl = imageProcessor.resolveImageUrl(
                    recipeId = editedRecipe.id,
                    step = step,
                    oldStep = null
                )
            )
        }

        val stepsToDelete = difference.stepsToDelete.map { step ->
            UploadRecipeStep(
                id = step.id,
                imageUrl = imageProcessor.resolveImageUrl(
                    recipeId = editedRecipe.id,
                    step = EditStep(id = step.id, imageSourceType = ImageSourceType.None),
                    oldStep = originalMap[step.id]
                )
            )
        }

        updateRecipeUseCase.execute(
            recipe = recipe,
            deleteSteps = stepsToDelete,
            addSteps = stepsToAdd,
            updateSteps = stepsToUpdate
        )
    }

    private suspend fun updateRecipeImageSource(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ): String? {
        return when (editedRecipe.imageSourceType) {
            is ImageSourceType.None -> {
                if (originalRecipe.imageSourceType is ImageSourceType.Remote) {
                    deleteRecipeImageUseCase.execute(editedRecipe.id)
                }
                null
            }

            is ImageSourceType.Remote -> editedRecipe.imageSourceType.source
            is ImageSourceType.Local -> {
                getRecipeCoverUrlUseCase.execute(
                    editedRecipe.id,
                    editedRecipe.imageSourceType.source
                )
            }
        }
    }
}