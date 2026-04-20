package com.example.recipebook.domain.model.error.validation

sealed interface ValidationError {
    data object Empty : ValidationError
    data object MinSymbolLimit: ValidationError
    data object MaxSymbolLimit : ValidationError
    data object NoSpecificSymbol: ValidationError
    data object None : ValidationError
}