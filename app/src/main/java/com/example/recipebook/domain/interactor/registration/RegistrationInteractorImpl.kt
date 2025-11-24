package com.example.recipebook.domain.interactor.registration

import com.example.recipebook.domain.interactor.profile.FirestoreRepository
import com.example.recipebook.domain.repository.FirebaseRepository
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firestoreRepository: FirestoreRepository
) : RegistrationInteractor {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        val authenticationResult =
            firebaseRepository.register(
                name = name,
                email = email,
                password = password
            )

        return authenticationResult.fold(
            onSuccess = { authenticatedUser ->
                firestoreRepository.createUserDocumentIfNeeded(authenticatedUser)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}