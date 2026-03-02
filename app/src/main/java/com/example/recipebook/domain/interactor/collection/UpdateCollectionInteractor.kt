package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollectionEdit

interface UpdateCollectionInteractor {
    suspend fun updateCollection(
        editedCollection: UserCollectionEdit,
        originalCollection: UserCollectionEdit
    )
}