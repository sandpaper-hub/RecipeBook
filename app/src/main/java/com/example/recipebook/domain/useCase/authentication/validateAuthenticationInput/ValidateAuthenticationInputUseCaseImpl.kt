package com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput

import com.example.recipebook.domain.model.error.authentication.AuthenticationError
import javax.inject.Inject

class ValidateAuthenticationInputUseCaseImpl @Inject constructor(): ValidateAuthenticationInputUseCase {
    override fun validateEmail(email: String): AuthenticationError.Email? = when {
        email.isBlank() -> AuthenticationError.Email.Empty
        else -> null
    }

    override fun validatePassword(password: String): AuthenticationError.Password? = when {
        password.isBlank() -> AuthenticationError.Password.Empty
        password.length < 6 -> AuthenticationError.Password.MinLength
        else -> null
    }
}