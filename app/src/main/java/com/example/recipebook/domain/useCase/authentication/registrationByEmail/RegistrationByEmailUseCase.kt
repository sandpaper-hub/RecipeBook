package com.example.recipebook.domain.useCase.authentication.registrationByEmail

interface RegistrationByEmailUseCase {
    suspend fun execute(
        name: String,
        email: String,
        password: String
    ): Result<Unit>
}