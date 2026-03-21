package com.example.recipebook.domain.useCase.authentication.loginByEmail

interface LoginByEmailUseCase {
    suspend fun execute(email: String, password: String): Result<Unit>
}