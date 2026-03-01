package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.model.collection.UserCollectionEdit
import com.example.recipebook.domain.model.recipe.step.ImageSourceType
import com.example.recipebook.domain.useCase.AddRecipeToCollectionUseCase
import com.example.recipebook.domain.useCase.CreateCollectionDocumentUseCase
import com.example.recipebook.domain.useCase.CreateCollectionUseCase
import com.example.recipebook.domain.useCase.DeleteCollectionUseCase
import com.example.recipebook.domain.useCase.GetUserCollectionUseCase
import com.example.recipebook.domain.useCase.GetUserCollectionsUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.ObserveCollectionDetailUseCase
import com.example.recipebook.domain.useCase.RemoveRecipeFromCollectionUseCase
import com.example.recipebook.domain.useCase.UploadCollectionCoverUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionInteractorImpl @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val createCollectionDocumentUseCase: CreateCollectionDocumentUseCase,
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase,
    private val getUserCollectionsUseCase: GetUserCollectionsUseCase,
    private val observeCollectionDetailUseCase: ObserveCollectionDetailUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val addRecipeToCollectionUseCase: AddRecipeToCollectionUseCase,
    private val removeRecipeFromCollectionUseCase: RemoveRecipeFromCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase

) : CollectionInteractor {
    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCase.execute()


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
                imageUrl = collectionImageSource,
                recipesCount = 0
            )
        )
    }

    override suspend fun addRecipeToCollection(
        collectionId: String,
        recipeId: String
    ) {
        addRecipeToCollectionUseCase.execute(collectionId = collectionId, recipeId = recipeId)
    }

    override suspend fun removeRecipeFromCollection(
        collectionId: String,
        recipeId: String
    ) {
        removeRecipeFromCollectionUseCase.execute(collectionId = collectionId, recipeId = recipeId)
    }

    override suspend fun deleteCollection(collectionId: String) {
        deleteCollectionUseCase.execute(collectionId)
    }

    override suspend fun getCollectionById(collectionId: String): UserCollectionEdit {
        val userCollection = getUserCollectionUseCase.execute(collectionId)
        return UserCollectionEdit(
            id = userCollection.id,
            name = userCollection.name,
            description = userCollection.description,
            recipeIds = userCollection.recipeIds,
            imageSource = if (userCollection.imageUrl == null) {
                ImageSourceType.None
            } else {
                ImageSourceType.Remote(userCollection.imageUrl)
            },
            createdAt = userCollection.createdAt
        )
    }
}