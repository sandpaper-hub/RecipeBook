package com.example.recipebook.domain.model

sealed interface DataError {
    object NoInternet: DataError
    object Timeout: DataError
    object Unknown: DataError
}