package com.example.recipebook.domain.useCase.collection.observeCollectionDetailUseCase

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface ObserveCollectionDetailUseCase {
    fun execute(userId: String, collectionId: String): Flow<UserCollection?>
}