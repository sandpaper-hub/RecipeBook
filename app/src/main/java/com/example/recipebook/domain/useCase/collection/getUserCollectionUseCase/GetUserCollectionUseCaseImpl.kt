package com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase

import com.example.recipebook.domain.model.collection.UserCollectionEdit
import com.example.recipebook.domain.model.recipe.step.ImageSourceType
import com.example.recipebook.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetUserCollectionUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
): GetUserCollectionUseCase {
    override suspend fun execute(collectionId: String): UserCollectionEdit {
        val userCollection = collectionsRepository.getCollectionById(collectionId)
        return UserCollectionEdit(
            id = userCollection.id,
            name = userCollection.name,
            description = userCollection.description,
            imageSource = if (userCollection.imageSource == null) {
                ImageSourceType.None
            } else {
                ImageSourceType.Remote(userCollection.imageSource)
            }
        )
    }
}