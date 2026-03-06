package com.example.recipebook.domain.useCase.collection.observeCollectionDetailUseCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class ObserveCollectionDetailUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
): ObserveCollectionDetailUseCase {
    override fun execute(userId: String, collectionId: String) =
        collectionsRepository.observeCollectionDetail(userId, collectionId)
}