package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class CreateCollectionDocumentUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend fun execute(): String =
        collectionsRepository.createDocument()
}