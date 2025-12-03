package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.useCase.UpdateUserProfileUseCase
import com.example.recipebook.domain.util.ImageCompressor
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val imageCompressor: ImageCompressor,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ProfileInteractor {
    override suspend fun getUserProfile(): Result<UserProfile> {
        return profileRepository.getUserProfile()
    }

    override suspend fun updateUserData(data: Map<String, String?>, uriString: String?): Result<Unit> {
        if (uriString == null) {
            return updateUserProfileUseCase.updateUserProfile(data, null)
        } else {
            val imageBytes = imageCompressor.compress(uriString)
            return updateUserProfileUseCase.updateUserProfile(data, imageBytes)
        }
    }
}