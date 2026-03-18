package com.example.recipebook.domain.useCase.userProfile.observeUserProfile

import com.example.recipebook.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow

interface ObserveUserProfileUseCase {
    fun execute(): Flow<UserProfile>
}