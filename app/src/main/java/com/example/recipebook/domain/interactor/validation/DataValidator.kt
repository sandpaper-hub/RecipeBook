package com.example.recipebook.domain.interactor.validation

import com.example.recipebook.domain.model.error.validation.ValidationError

interface DataValidator {
    fun validateStringLength(value: String, lengthLimit: Int): ValidationError
    fun validateIsEmpty(value: String): ValidationError
    fun validateTimeEstimation(hour: Int, minute: Int): ValidationError
    fun <T> validateObjectMinCount(ingredientList: List<T>, countLimit: Int): Boolean
    fun <T> validateObjectMaxCount(ingredientList: List<T>, countLimit: Int): Boolean
    fun validateIngredient(
        value: String,
        amount: String,
        measure: String?
    ): ValidationError

    fun validateStepValue(
        value: String
    ): ValidationError
}