package com.example.recipebook.domain.interactor.collection.createCollectionInteractor

interface CreateCollectionInteractor {
    suspend fun createCollection(
        name: String,
        description: String,
        imageSource: String?
    ): Result<Unit>
}