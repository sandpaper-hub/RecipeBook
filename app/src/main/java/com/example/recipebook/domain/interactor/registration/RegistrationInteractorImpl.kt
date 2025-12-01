package com.example.recipebook.domain.interactor.registration

import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.util.convertToNickName
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : RegistrationInteractor {
    override suspend fun register(
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

        return authenticationResult.fold(
            onSuccess = { authenticatedUser ->
                authenticationRepository.createUserDocumentIfNeeded(authenticatedUser)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}