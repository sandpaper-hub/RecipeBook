package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.useCase.CreateCollectionDocumentUseCase
import com.example.recipebook.domain.useCase.CreateCollectionUseCase
import com.example.recipebook.domain.useCase.DeleteCollectionUseCase
import com.example.recipebook.domain.useCase.GetUserCollectionsUseCase
import com.example.recipebook.domain.useCase.getUserIdFlow.GetUserIdFlowUseCaseImpl
import com.example.recipebook.domain.useCase.ObserveCollectionDetailUseCase
import com.example.recipebook.domain.useCase.UploadCollectionCoverUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionInteractorImpl @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val createCollectionDocumentUseCase: CreateCollectionDocumentUseCase,
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase,
    private val getUserCollectionsUseCase: GetUserCollectionsUseCase,
    private val observeCollectionDetailUseCase: ObserveCollectionDetailUseCase,
    private val getUserIdFlowUseCaseImpl: GetUserIdFlowUseCaseImpl,
    private val deleteCollectionUseCase: DeleteCollectionUseCase
) : CollectionInteractor {
    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCaseImpl.execute()


    override fun observeUserCollections(userId: String): Flow<List<UserCollection>> =
        getUserCollectionsUseCase.execute(userId)


    override fun observeCollectionDetail(
        userId: String,
        collectionId: String
    ): Flow<UserCollection?> = observeCollectionDetailUseCase.execute(userId, collectionId)


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

    override suspend fun deleteCollection(collectionId: String) {
        deleteCollectionUseCase.execute(collectionId)
    }
}