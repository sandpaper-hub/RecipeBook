package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    fun observeUserCollections(userId: String): Flow<List<UserCollection>>

    fun observeCollectionDetail(userId: String, collectionId: String): Flow<UserCollection?>
    suspend fun createDocument(): String
    suspend fun createCollection(
        userCollection: UserCollection
    ): Result<Unit>

    suspend fun uploadCollectionImage(
        collectionId: String,
        imageSource: String
    ): String

    suspend fun addRecipeToCollection(collectionId: String, recipeId: String)

    suspend fun removeRecipeFromCollection(collectionId: String, recipeId: String)
    suspend fun deleteCollection(collectionId: String)
    suspend fun getCollectionById(collectionId: String): UserCollection
}