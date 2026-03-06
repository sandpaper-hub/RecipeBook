package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.useCase.CreateCollectionDocumentUseCase
import com.example.recipebook.domain.useCase.CreateCollectionUseCase
import com.example.recipebook.domain.useCase.UploadCollectionCoverUseCase
import javax.inject.Inject

class CreateCreateCollectionInteractorImpl @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val createCollectionDocumentUseCase: CreateCollectionDocumentUseCase,
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase
) : CreateCollectionInteractor {
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
            UserCollection(
                id = collectionId,
                name = name,
                description = description,
                imageSource = collectionImageSource
            )
        )
    }
}