package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.UserProfile

interface ProfileRepository {
    suspend fun createUserDocumentIfNeeded(userProfile: UserProfile): Result<Unit>
    suspend fun getUserProfile(): Result<UserProfile>
}