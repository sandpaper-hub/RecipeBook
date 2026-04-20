package com.example.recipebook.domain.service.validation

import com.example.recipebook.domain.model.error.validation.ValidationError

interface DataValidator {
    fun validateStringLength(value: String, lengthLimit: Int): ValidationError
    fun validateStringLength(value: String, maxLength: Int, minLength: Int): ValidationError
    fun validateIsEmpty(value: String): ValidationError
    fun validateTimeEstimation(hour: Int, minute: Int): ValidationError
    fun <T> validateObjectMinCount(objectsList: List<T>, countLimit: Int): Boolean
    fun <T> validateObjectMaxCount(objectsList: List<T>, countLimit: Int): Boolean
    fun validateNickName(value: String, lengthLimit: Int): ValidationError

    fun validateIngredient(
        value: String,
        amount: String,
        measure: String?
    ): ValidationError

    fun validateStepValue(
        value: String
    ): ValidationError
}