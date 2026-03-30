package com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput

import com.example.recipebook.domain.model.error.authentication.AuthenticationError

interface ValidateAuthenticationInputUseCase {
    fun validateEmail(email: String): AuthenticationError.Email?
    fun validatePassword(password: String): AuthenticationError.Password?
}