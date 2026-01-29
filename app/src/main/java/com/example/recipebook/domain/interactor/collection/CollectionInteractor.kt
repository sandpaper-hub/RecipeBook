package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface CollectionInteractor {

    fun getUserIdFlow(): Flow<String?>

    fun observeUserCollections(userId: String): Flow<List<UserCollection>>
    suspend fun createCollection(
        name: String,
        description: String,
        imageSource: String?
    ): Result<Unit>
}