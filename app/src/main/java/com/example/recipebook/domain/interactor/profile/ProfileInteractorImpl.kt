package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.util.ImageCompressor
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor (
    private val profileRepository: ProfileRepository,
    private val imageCompressor: ImageCompressor) : ProfileInteractor {
    override suspend fun getUserProfile(): Result<UserProfile> {
        return profileRepository.getUserProfile()
    }

    override suspend fun updateUserData(data: Map<String, String?>, uriString: String): Result<Unit> {
        val compressedBytes = imageCompressor.compress(uriString)
        return profileRepository.updateUserData(data, compressedBytes)
    }
}