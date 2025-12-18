package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.useCase.GetLocalesUseCase
import com.example.recipebook.domain.useCase.UpdateUserProfileUseCase
import com.example.recipebook.domain.useCase.UploadUserProfileUseCase
import com.example.recipebook.domain.util.ImageCompressor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor(
    private val imageCompressor: ImageCompressor,
    private val uploadUserProfileUseCase: UploadUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getLocalesUseCase: GetLocalesUseCase
) : ProfileInteractor {
    override fun observerUserProfile(): Flow<UserProfile> =
        uploadUserProfileUseCase.observeUserProfile()


    override suspend fun updateUserData(data: Map<String, Any?>, uriString: String?): Result<Unit> {
        if (uriString == null) {
            return updateUserProfileUseCase.execute(data, null)
        } else {
            val imageBytes = imageCompressor.compress(uriString)
            return updateUserProfileUseCase.execute(data, imageBytes)
        }
    }

    override fun getLocales(): List<String> =
        getLocalesUseCase.execute()
}