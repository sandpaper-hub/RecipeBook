package com.example.recipebook.domain.interactor.login

interface LoginInteractor {
    suspend fun loginByEmail(email: String, password: String): Result<Unit>
}