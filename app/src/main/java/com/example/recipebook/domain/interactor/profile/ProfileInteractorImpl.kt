package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor (
    private val profileRepository: ProfileRepository) : ProfileInteractor {
    override suspend fun getUserProfile(): Result<UserProfile> {
        return profileRepository.getUserProfile()
    }
}