package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.collection.UserCollection

interface CollectionsRepository {
    suspend fun createDocument(): String
    suspend fun createCollection(
        collectionId: String,
        userCollection: UserCollection
    ): Result<Unit>

    suspend fun uploadCollectionImage(
        collectionId: String,
        imageSource: String
    ) : String
}