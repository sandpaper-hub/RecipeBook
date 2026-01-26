package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(collectionId: String, userCollection: UserCollection): Result<Unit>{
       return collectionsRepository.createCollection(collectionId, userCollection)
    }
}