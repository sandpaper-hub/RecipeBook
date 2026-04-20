package com.example.recipebook.domain.model.error.dataError

sealed class DataError : Exception(){
    class Unavailable: DataError()
    class Unknown(override val message: String?): DataError()
}