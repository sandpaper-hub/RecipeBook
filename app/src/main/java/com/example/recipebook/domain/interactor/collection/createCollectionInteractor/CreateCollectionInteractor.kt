package com.example.recipebook.domain.interactor.collection.createCollectionInteractor

import com.example.recipebook.domain.model.ImageSourceType

interface CreateCollectionInteractor {
    suspend fun createCollection(
        name: String,
        description: String,
        imageSource: ImageSourceType
    ): Result<Unit>
}