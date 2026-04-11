package com.example.recipebook.presentation.validator

import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CollectionFormUiState
import javax.inject.Inject

class CollectionValidator @Inject constructor(
    private val dataValidator: DataValidator
) {
    fun validateAll(state: CollectionFormUiState): Pair<CollectionFormUiState, Boolean> {
        var isValid = true
        var validatedState = state

        val nameError = dataValidator.validateIsEmpty(state.name.value)
        when {
            state.name.error is ValidationError.SymbolLimit -> isValid = false
            nameError is ValidationError.Empty -> {
                validatedState = validatedState.copy(
                    name = validatedState.name.copy(
                        error = nameError
                    )
                )
                isValid = false
            }
        }

        return validatedState to isValid
    }
}