package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfile>

    fun observeUserProfile(): Flow<UserProfile>

    suspend fun uploadUserAvatar(bytes: ByteArray): Result<String>

    suspend fun updateUserData(data: Map<String, Any?>): Result<Unit>
}