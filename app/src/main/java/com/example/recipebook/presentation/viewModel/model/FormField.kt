package com.example.recipebook.presentation.viewModel.model

import com.example.recipebook.domain.model.error.validation.ValidationError

data class FormField<T>(
    val value: T,
    val error: ValidationError = ValidationError.None
){
    val isValid: Boolean get() = error == ValidationError.None
}
