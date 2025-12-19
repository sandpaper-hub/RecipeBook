package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginByEmailUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(email: String, password: String): Result<Unit> =
        authenticationRepository.signIn(email = email, password = password)
}