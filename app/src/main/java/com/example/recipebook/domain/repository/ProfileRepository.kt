package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfile>

    suspend fun updateUserData(data: Map<String, String?>, bytes: ByteArray): Result<Unit>
}