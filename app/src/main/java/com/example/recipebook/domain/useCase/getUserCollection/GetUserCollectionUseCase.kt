package com.example.recipebook.domain.useCase.getUserCollection

import com.example.recipebook.domain.model.collection.UserCollectionEdit

interface GetUserCollectionUseCase {
    suspend fun execute(collectionId: String): UserCollectionEdit
}