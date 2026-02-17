package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class ObserveCollectionDetailUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    fun execute(userId: String, collectionId: String) =
        collectionsRepository.observeCollectionDetail(userId, collectionId)
}