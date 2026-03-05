package com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserCollectionUseCaseImpl @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ObserveUserCollectionUseCase {
    override fun execute(userId: String): Flow<List<UserCollection>> =
        collectionsRepository.observeUserCollections(userId)

}