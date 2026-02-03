package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    fun observerUserProfile(): Flow<UserProfile>
    suspend fun updateUserData(data: Map<String, Any?>, uriString: String?): Result<Unit>
    fun getLocales(): List<String>
    fun getUserIdFlow(): Flow<String?>
}