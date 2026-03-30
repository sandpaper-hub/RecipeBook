package com.example.recipebook.domain.model.error

sealed interface SearchDataError {
    object NoInternet: SearchDataError
    object Timeout: SearchDataError
    object Unknown: SearchDataError
}