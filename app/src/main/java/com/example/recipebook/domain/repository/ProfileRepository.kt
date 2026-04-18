package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun observeUserProfile(): Flow<UserProfile>
    suspend fun uploadUserAvatar(imageSource: String): String
    suspend fun updateUserData(userProfile: UserProfile): Result<Unit>
    fun currentUserUidFlow(): Flow<String?>
}