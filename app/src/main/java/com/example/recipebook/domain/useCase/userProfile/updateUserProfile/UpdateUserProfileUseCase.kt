package com.example.recipebook.domain.useCase.userProfile.updateUserProfile

import com.example.recipebook.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(data: Map<String, Any?>): Result<Unit> {
        return profileRepository.updateUserData(data)
    }
}