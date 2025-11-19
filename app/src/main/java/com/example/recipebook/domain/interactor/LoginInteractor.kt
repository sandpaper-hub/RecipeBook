package com.example.recipebook.domain.interactor

interface LoginInteractor {
    suspend fun signIn(email: String, password: String): Result<Unit>
}