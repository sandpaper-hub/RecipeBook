package com.example.recipebook.domain.useCase.authentication

import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.presentation.util.convertToNickName
import javax.inject.Inject

class RegistrationByEmailUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        val authenticationResult =
            authenticationRepository.register(
                name = name,
                email = email,
                password = password,
                nickName = email.convertToNickName()
            )

        val user = authenticationResult.getOrElse { return Result.failure(it) }
        return authenticationRepository.createUserDocumentIfNeeded(user)
    }
}