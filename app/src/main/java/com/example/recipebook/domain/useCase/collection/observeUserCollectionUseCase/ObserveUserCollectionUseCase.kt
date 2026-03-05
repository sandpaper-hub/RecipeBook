package com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface ObserveUserCollectionUseCase {
    fun execute(userId: String): Flow<List<UserCollection>>
}