package com.example.recipebook.domain.interactor.profile.updateProfile

import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.useCase.userProfile.updateUserProfile.UpdateUserProfileUseCase
import com.example.recipebook.domain.useCase.userProfile.updateUserProfile.UploadUserAvatarUseCase
import javax.inject.Inject

class UpdateUserDataInteractorImpl @Inject constructor(
    private val uploadUserAvatarUseCase: UploadUserAvatarUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : UpdateUserDataInteractor {
    override suspend fun invoke(
        image: ImageSourceType,
        fullName: String,
        nickName: String,
        region: String,
        dateOdBirth: Long,
        gender: String
    ): Result<Unit> {
        val photoUrl = when (image) {
            is ImageSourceType.Local -> uploadUserAvatarUseCase.execute(image.source)
            is ImageSourceType.Remote -> image.source
            is ImageSourceType.None -> null
        }
        val userProfile = UserProfile(
            fullName = fullName,
            nickName = nickName,
            region = region,
            dateOfBirth = dateOdBirth,
            gender = gender,
            photoUrl = photoUrl
        )
        return updateUserProfileUseCase.execute(userProfile)
    }
}