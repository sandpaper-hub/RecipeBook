package com.example.recipebook.domain.interactor.registration

import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val profileRepository: ProfileRepository
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
                password = password
            )

        return authenticationResult.fold(
            onSuccess = { authenticatedUser ->
                profileRepository.createUserDocumentIfNeeded(authenticatedUser)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}