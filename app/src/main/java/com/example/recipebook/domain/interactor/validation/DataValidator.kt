package com.example.recipebook.domain.interactor.validation

import com.example.recipebook.domain.model.error.validation.ValidationError

interface DataValidator {
    fun validateStringLength(value: String, lengthLimit: Int): ValidationError
    fun validateIsEmpty(value: String): ValidationError
    fun validateTimeEstimation(hour: Int, minute: Int): ValidationError
    fun <T> validateIngredientMinCount(ingredientList: List<T>): Boolean
    fun <T> validateIngredientMaxCount(ingredientList: List<T>): Boolean
}