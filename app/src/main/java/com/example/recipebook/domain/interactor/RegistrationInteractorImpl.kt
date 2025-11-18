package com.example.recipebook.domain.interactor

import com.example.recipebook.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val registrationRepository: RegistrationRepository
) : RegistrationInteractor {
    override fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        registrationRepository.register(
            name = name,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onError = onError)
    }
}