package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun observeUserProfile(): Flow<UserProfile> = profileRepository.observeUserProfile()
}