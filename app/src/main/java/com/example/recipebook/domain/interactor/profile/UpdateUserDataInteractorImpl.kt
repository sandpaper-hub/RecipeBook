package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.useCase.userProfile.UpdateUserProfileUseCase
import com.example.recipebook.domain.useCase.userProfile.UploadUserAvatarUseCase
import javax.inject.Inject

class UpdateUserDataInteractorImpl @Inject constructor(
    private val uploadUserAvatarUseCase: UploadUserAvatarUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : UpdateUserDataInteractor {
    override suspend fun invoke(
        data: Map<String, Any?>,
        uriString: String?
    ): Result<Unit> {
        val mutableData: MutableMap<String, Any?> = data.toMutableMap()

        if (uriString != null) {
            val userAvatarSource = uploadUserAvatarUseCase.execute(uriString)
            mutableData["photoUrl"] = userAvatarSource
        } else {
            mutableData["photoUrl"] = null
        }
        return updateUserProfileUseCase.execute(mutableData)
    }
}