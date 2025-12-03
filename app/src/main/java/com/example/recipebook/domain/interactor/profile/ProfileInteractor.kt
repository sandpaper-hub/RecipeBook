package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    fun observerUserProfile(): Flow<UserProfile>
    suspend fun updateUserData(data: Map<String, String?>, uriString: String?): Result<Unit>
}