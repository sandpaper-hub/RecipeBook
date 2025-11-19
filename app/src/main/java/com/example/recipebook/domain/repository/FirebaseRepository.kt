package com.example.recipebook.domain.repository

interface FirebaseRepository {
    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signIn(email: String, password: String): Result<Unit>

    fun isLoggedIn(): Boolean
}