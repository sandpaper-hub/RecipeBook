package com.example.recipebook.domain.useCase.collection

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class UploadCollectionCoverUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(collectionId: String, imageSource: String) =
        collectionsRepository.uploadCollectionImage(collectionId, imageSource)
}