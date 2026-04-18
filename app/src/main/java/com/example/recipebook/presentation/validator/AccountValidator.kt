package com.example.recipebook.presentation.validator

import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.domain.service.validation.DataValidator
import com.example.recipebook.presentation.viewModel.accountScreen.model.AccountUiState
import javax.inject.Inject

class AccountValidator @Inject constructor(private val dataValidator: DataValidator) {
    fun validateAll(state: AccountUiState): Pair<AccountUiState, Boolean> {
        var isValid = true
        var validatedState = state

        val nameError = dataValidator.validateIsEmpty(
            value = state.fullName.value
        )
        when {
            state.fullName.error is ValidationError.MaxSymbolLimit -> isValid = false
            nameError is ValidationError.Empty -> isValid = false
        }

        val nickNameError = dataValidator.validateStringLength(
            value = state.nickName.value,
            maxLength = Constraints.MAX_NICKNAME_LENGTH,
            minLength = Constraints.MIN_NICKNAME_LENGTH
        )

        when {
            state.nickName.error is ValidationError.MaxSymbolLimit -> isValid = false
            nickNameError is ValidationError.MinSymbolLimit -> isValid = false
            nickNameError is ValidationError.Empty -> isValid = false
        }

        validatedState = validatedState.copy(
            fullName = validatedState.fullName.copy(
                error = nameError
            ),
            nickName = validatedState.nickName.copy(
                error = nickNameError
            )
        )

        return validatedState to isValid
    }
}