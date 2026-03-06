package com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor

interface DeleteCollectionInteractor {
    suspend fun invoke(collectionId: String, recipeIds: List<String>)
}