package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetUserCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(collectionId: String): UserCollection {
        return collectionsRepository.getCollectionById(collectionId)
    }
}