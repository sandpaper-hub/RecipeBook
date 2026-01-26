package com.example.recipebook.domain.interactor.collection

interface CollectionInteractor {
    suspend fun createCollection(
        name: String,
        description: String,
        imageSource: String?
    ): Result<Unit>
}