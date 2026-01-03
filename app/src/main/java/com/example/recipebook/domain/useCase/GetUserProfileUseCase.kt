package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun execute(): Flow<UserProfile> = profileRepository.observeUserProfile()
}