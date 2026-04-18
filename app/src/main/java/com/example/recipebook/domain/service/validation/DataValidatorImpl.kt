package com.example.recipebook.domain.service.validation

import com.example.recipebook.domain.model.error.validation.ValidationError
import javax.inject.Inject

class DataValidatorImpl @Inject constructor() : DataValidator {
    override fun validateStringLength(value: String, lengthLimit: Int): ValidationError {
        return if (value.length > lengthLimit) {
            ValidationError.MaxSymbolLimit
        } else {
            ValidationError.None
        }
    }

    override fun validateStringLength(
        value: String,
        maxLength: Int,
        minLength: Int
    ): ValidationError {
        return when{
            value.isBlank() -> ValidationError.Empty
            value.length > maxLength -> ValidationError.MaxSymbolLimit
            value.length < minLength -> ValidationError.MinSymbolLimit
            else -> ValidationError.None
        }
    }

    override fun validateIsEmpty(value: String): ValidationError {
        return if (value.isBlank()) ValidationError.Empty else ValidationError.None
    }

    override fun validateTimeEstimation(hour: Int, minute: Int): ValidationError {
        return if (hour == 0 && minute == 0) {
            ValidationError.Empty
        } else {
            ValidationError.None
        }
    }

    override fun <T> validateObjectMinCount(objectsList: List<T>, countLimit: Int): Boolean {
        return objectsList.size > countLimit
    }

    override fun <T> validateObjectMaxCount(objectsList: List<T>, countLimit: Int): Boolean {
        return objectsList.size < countLimit
    }

    override fun validateNickName(value: String, lengthLimit: Int): ValidationError {
        val allowedRegex = Regex("^[A-Za-z0-9._]*$")
        return if (allowedRegex.matches(value)){
            validateStringLength(value, lengthLimit)
        } else{
            ValidationError.NoSpecificSymbol
        }
    }

    override fun validateIngredient(
        value: String,
        amount: String,
        measure: String?
    ): ValidationError {
        return listOf(
            validateIsEmpty(value),
            validateIsEmpty(amount),
            validateMeasure(measure)
        ).firstOrNull { it != ValidationError.None } ?: ValidationError.None
    }

    override fun validateStepValue(value: String): ValidationError {
        return validateIsEmpty(value)
    }

    private fun validateMeasure(measure: String?): ValidationError {
        return if (measure == null) {
            ValidationError.Empty
        } else {
            ValidationError.None
        }
    }
}
