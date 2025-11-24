package com.example.recipebook.domain.interactor.login

interface LoginInteractor {
    suspend fun signIn(email: String, password: String): Result<Unit>
}