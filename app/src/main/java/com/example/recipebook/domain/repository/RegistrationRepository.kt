package com.example.recipebook.domain.repository

interface RegistrationRepository {
    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}