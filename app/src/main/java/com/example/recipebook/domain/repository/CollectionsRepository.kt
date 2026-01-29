package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    fun observeUserCollections(userId: String): Flow<List<UserCollection>>
    suspend fun createDocument(): String
    suspend fun createCollection(
        userCollection: UserCollection
    ): Result<Unit>

    suspend fun uploadCollectionImage(
        collectionId: String,
        imageSource: String
    ) : String
}