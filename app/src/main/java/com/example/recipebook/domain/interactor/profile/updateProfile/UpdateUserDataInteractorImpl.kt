package com.example.recipebook.domain.interactor.profile.updateProfile

import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.useCase.userProfile.updateUserProfile.UpdateUserProfileUseCase
import com.example.recipebook.domain.useCase.userProfile.updateUserProfile.UploadUserAvatarUseCase
import javax.inject.Inject

class UpdateUserDataInteractorImpl @Inject constructor(
    private val uploadUserAvatarUseCase: UploadUserAvatarUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : UpdateUserDataInteractor {
    override suspend fun invoke(
        data: Map<String, Any?>,
        imageSource: ImageSourceType
    ): Result<Unit> {
        val mutableData: MutableMap<String, Any?> = data.toMutableMap()

        if (imageSource is ImageSourceType.Local) {
            val userAvatarSource = uploadUserAvatarUseCase.execute(imageSource.source)
            mutableData["photoUrl"] = userAvatarSource
        }

        return updateUserProfileUseCase.execute(mutableData)
    }
}