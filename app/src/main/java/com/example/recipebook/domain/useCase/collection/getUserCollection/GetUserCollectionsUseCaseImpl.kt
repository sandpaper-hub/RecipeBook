package com.example.recipebook.domain.useCase.collection.getUserCollection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCollectionsUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
): GetUserCollectionUseCase {
    override fun execute(userId: String): Flow<List<UserCollection>> =
        collectionsRepository.observeUserCollections(userId)
}