package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile

interface ProfileInteractor {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun updateUserData(data: Map<String, Any>): Result<Unit>
}