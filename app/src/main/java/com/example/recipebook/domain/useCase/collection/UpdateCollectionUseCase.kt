package com.example.recipebook.domain.useCase.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class UpdateCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(userCollection: UserCollection) {
        collectionsRepository.updateCollection(userCollection)
    }
}