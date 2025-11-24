package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.UserProfile

interface AuthenticationRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String,
    ) : Result<UserProfile>

    suspend fun signIn(email: String, password: String): Result<Unit>

    fun isLoggedIn(): Boolean
}