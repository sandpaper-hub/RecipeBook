package com.example.recipebook.domain.interactor.profile.updateProfile

import com.example.recipebook.domain.model.ImageSourceType

interface UpdateUserDataInteractor {
    suspend fun invoke(
        image: ImageSourceType,
        fullName: String,
        nickName: String,
        region: String,
        dateOdBirth: Long,
        gender: String
    ): Result<Unit>
}