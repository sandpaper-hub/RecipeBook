package com.example.recipebook.domain.model

sealed interface AppResult <out T>{
    data class Success<T>(
        val data: T
    ): AppResult<T>

    data class Error(val error: DataError): AppResult<Nothing>
}