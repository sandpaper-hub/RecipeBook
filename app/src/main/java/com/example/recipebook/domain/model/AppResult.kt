package com.example.recipebook.domain.model

import com.example.recipebook.domain.model.error.SearchDataError

sealed interface AppResult <out T>{
    data class Success<T>(
        val data: T
    ): AppResult<T>

    data class Error(val error: SearchDataError): AppResult<Nothing>
}