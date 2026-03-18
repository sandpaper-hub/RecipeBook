package com.example.recipebook.domain.interactor.profile.updateProfile

import com.example.recipebook.domain.model.ImageSourceType

interface UpdateUserDataInteractor {
    suspend fun invoke(data: Map<String, Any?>, imageSource: ImageSourceType): Result<Unit>
}