package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.profile.UserProfile

interface AuthenticationRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String,
        nickName: String
    ) : Result<UserProfile>

    suspend fun signIn(email: String, password: String): Result<Unit>

    suspend fun createUserDocumentIfNeeded(userProfile: UserProfile): Result<Unit>

    fun isLoggedIn(): Boolean

    suspend fun logOut()

    fun getCurrentUserId(): String?
}