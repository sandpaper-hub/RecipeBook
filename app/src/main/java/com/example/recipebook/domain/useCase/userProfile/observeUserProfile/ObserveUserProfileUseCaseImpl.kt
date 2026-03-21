package com.example.recipebook.domain.useCase.userProfile.observeUserProfile

import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : ObserveUserProfileUseCase{
    override fun execute(): Flow<UserProfile> = profileRepository.observeUserProfile()
}