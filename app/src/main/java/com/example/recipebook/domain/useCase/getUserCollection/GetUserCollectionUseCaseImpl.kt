package com.example.recipebook.domain.useCase.getUserCollection

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