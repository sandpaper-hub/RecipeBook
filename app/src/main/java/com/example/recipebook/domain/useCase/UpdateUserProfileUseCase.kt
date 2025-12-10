package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(
        data: Map<String, String?>,
        imageBytes: ByteArray?
    ): Result<Unit> {
        val mutableData: MutableMap<String, Any?> = data.toMutableMap()
        if (imageBytes != null) {
            val uploadResult = profileRepository.uploadUserAvatar(imageBytes)
            if (uploadResult.isFailure) {
                return Result.failure(uploadResult.exceptionOrNull()!!)
            }

            val photoUrl = uploadResult.getOrNull()
            mutableData["photoUrl"] = photoUrl
        }
        return profileRepository.updateUserData(mutableData)
    }
}