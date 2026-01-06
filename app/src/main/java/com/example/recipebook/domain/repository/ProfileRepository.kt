package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun observeUserProfile(): Flow<UserProfile>

    suspend fun uploadUserAvatar(bytes: ByteArray): Result<String>

    suspend fun updateUserData(data: Map<String, Any?>): Result<Unit>
    fun observeUserRecipes(userId: String): Flow<List<Recipe>>
    fun currentUserUidFlow(): Flow<String?>
}