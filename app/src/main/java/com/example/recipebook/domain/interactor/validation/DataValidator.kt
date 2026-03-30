package com.example.recipebook.domain.interactor.validation

import com.example.recipebook.domain.model.error.validation.ValidationError

interface DataValidator {
    fun validateStringLength(value: String, lengthLimit: Int): ValidationError
}