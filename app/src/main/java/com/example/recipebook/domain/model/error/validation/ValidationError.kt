package com.example.recipebook.domain.model.error.validation

sealed interface ValidationError {
    data object Empty : ValidationError
    data object SymbolLimit : ValidationError
    data object None : ValidationError
}