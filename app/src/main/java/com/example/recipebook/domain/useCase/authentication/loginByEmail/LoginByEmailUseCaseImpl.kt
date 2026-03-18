package com.example.recipebook.domain.useCase.authentication.loginByEmail

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginByEmailUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): LoginByEmailUseCase {
    override suspend fun execute(email: String, password: String): Result<Unit> =
        authenticationRepository.signIn(email = email, password = password)
}