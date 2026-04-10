package com.example.recipebook.domain.interactor.collection.createCollectionInteractor

import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.useCase.collection.CreateCollectionDocumentUseCase
import com.example.recipebook.domain.useCase.collection.CreateCollectionUseCase
import com.example.recipebook.domain.useCase.collection.UploadCollectionCoverUseCase
import javax.inject.Inject

class CreateCollectionInteractorImpl @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val createCollectionDocumentUseCase: CreateCollectionDocumentUseCase,
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase
) : CreateCollectionInteractor {
    override suspend fun createCollection(
        name: String,
        description: String,
        imageSource: ImageSourceType
    ): Result<Unit> {
        val collectionId = createCollectionDocumentUseCase.execute()
        val collectionImageSource = if (imageSource is ImageSourceType.Local) {
            uploadCollectionCoverUseCase.execute(collectionId, imageSource.source)
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