package com.example.recipebook.domain.interactor

interface RegistrationInteractor {
    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}