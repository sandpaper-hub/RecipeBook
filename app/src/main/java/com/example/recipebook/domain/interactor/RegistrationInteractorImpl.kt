package com.example.recipebook.domain.interactor

import com.example.recipebook.domain.repository.FirebaseRepository
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : RegistrationInteractor {
    override fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseRepository.register(
            name = name,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onError = onError)
    }
}