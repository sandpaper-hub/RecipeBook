package com.example.recipebook.domain.useCase.userProfile

import com.example.recipebook.domain.repository.ProfileRepository
import javax.inject.Inject

class UploadUserAvatarUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(imageSource: String): String {
        return profileRepository.uploadUserAvatar(imageSource)
    }
}