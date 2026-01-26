package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.useCase.CreateCollectionDocumentUseCase
import com.example.recipebook.domain.useCase.CreateCollectionUseCase
import com.example.recipebook.domain.useCase.UploadCollectionCoverUseCase
import javax.inject.Inject

class CollectionInteractorImpl @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val createCollectionDocumentUseCase: CreateCollectionDocumentUseCase,
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase
) : CollectionInteractor {
    override suspend fun createCollection(
        name: String,
        description: String,
        imageSource: String?
    ): Result<Unit> {
        val collectionId = createCollectionDocumentUseCase.execute()
        val collectionImageSource = if (imageSource != null) {
            uploadCollectionCoverUseCase.execute(collectionId, imageSource)
        } else null

        return createCollectionUseCase.execute(
            collectionId = collectionId,
            UserCollection(
                name = name,
                description = description,
                imageUrl = collectionImageSource
            )
        )
    }
}