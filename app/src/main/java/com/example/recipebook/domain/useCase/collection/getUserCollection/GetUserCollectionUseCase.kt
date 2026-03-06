package com.example.recipebook.domain.useCase.collection.getUserCollection

import com.example.recipebook.domain.model.collection.UserCollection
import kotlinx.coroutines.flow.Flow

interface GetUserCollectionUseCase {
    fun execute(userId: String): Flow<List<UserCollection>>
}