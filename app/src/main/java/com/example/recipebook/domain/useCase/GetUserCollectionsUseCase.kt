package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCollectionsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    fun execute(userId: String): Flow<List<UserCollection>> =
        collectionsRepository.observeUserCollections(userId)
}