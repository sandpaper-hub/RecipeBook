package com.example.recipebook.presentation.validator

import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import javax.inject.Inject

class RecipeValidator @Inject constructor(
    private val dataValidator: DataValidator
) {
    fun validateAll(state: NewRecipeUiState): Pair<NewRecipeUiState, Boolean> {
        var isValid = true
        var validatedState = state


        val nameError = dataValidator.validateIsEmpty(state.recipeName.value)
        when {
            state.recipeName.error is ValidationError.SymbolLimit -> isValid = false
            nameError is ValidationError.Empty -> {
                validatedState = validatedState.copy(
                    recipeName = validatedState.recipeName.copy(error = nameError)
                )
                isValid = false
            }
        }

        val descriptionError = dataValidator.validateIsEmpty(state.description.value)
        when {
            state.description.error is ValidationError.SymbolLimit -> isValid = false
            descriptionError is ValidationError.Empty -> {
                validatedState = validatedState.copy(
                    description = validatedState.description.copy(error = descriptionError)
                )
                isValid = false
            }
        }

        val categoryError = dataValidator.validateIsEmpty(state.recipeCategory.value)
        if (categoryError != ValidationError.None) {
            validatedState = validatedState.copy(
                recipeCategory = validatedState.recipeCategory.copy(error = categoryError)
            )
            isValid = false
        }

        val timeError = dataValidator.validateTimeEstimation(
            hour = state.timeEstimationUiState.hour,
            minute = state.timeEstimationUiState.minute
        )
        if (timeError != ValidationError.None) {
            validatedState = validatedState.copy(
                timeEstimationUiState = validatedState.timeEstimationUiState.copy(
                    error = timeError
                )
            )
            isValid = false
        }

        val validatedIngredients = state.ingredients.map { ingredient ->
            val ingredientError = dataValidator.validateIngredient(
                value = ingredient.value,
                amount = ingredient.amount,
                measure = if (ingredient.measure == MeasureMenuItem.NULL) null else ingredient.measure.toString()
            )

            if (ingredientError != ValidationError.None) isValid = false
            ingredient.copy(error = ingredientError)

        }

        val validatedSteps = state.recipeSteps.map { step ->
            val stepTitleError = if (step.title.error != ValidationError.None) {
                step.title.error
            } else {
                dataValidator.validateStepValue(
                    value = step.title.value
                )
            }
            val stepDescriptionError = if (step.description.error != ValidationError.None) {
                step.description.error
            } else {
                dataValidator.validateStepValue(
                    value = step.description.value
                )
            }

            when {
                stepTitleError != ValidationError.None -> isValid = false
                stepDescriptionError != ValidationError.None -> isValid = false
            }
            step.copy(
                title = step.title.copy(error = stepTitleError),
                description = step.description.copy(error = stepDescriptionError)
            )
        }

        validatedState = validatedState.copy(
            ingredients = validatedIngredients,
            recipeSteps = validatedSteps
        )

        return validatedState to isValid
    }
}