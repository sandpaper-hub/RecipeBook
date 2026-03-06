package com.example.recipebook.domain.interactor.collection

interface CreateCollectionInteractor {
    suspend fun createCollection(
        name: String,
        description: String,
        imageSource: String?
    ): Result<Unit>
}