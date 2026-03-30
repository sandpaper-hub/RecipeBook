package com.example.recipebook.domain.interactor.validation

import com.example.recipebook.domain.model.error.validation.ValidationError
import javax.inject.Inject

class DataValidatorImpl @Inject constructor() : DataValidator {
    override fun validateStringLength(value: String, lengthLimit: Int): ValidationError {
        return if (value.length > lengthLimit) {
            ValidationError.SymbolLimit
        } else {
            ValidationError.None
        }
    }


}
