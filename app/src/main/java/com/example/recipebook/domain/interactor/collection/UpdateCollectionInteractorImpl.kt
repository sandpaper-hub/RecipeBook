package com.example.recipebook.domain.interactor.collection

import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.model.collection.UserCollectionEdit
import com.example.recipebook.domain.model.recipe.step.ImageSourceType
import com.example.recipebook.domain.useCase.UploadCollectionCoverUseCase
import com.example.recipebook.domain.useCase.updateCollectionUseCase.UpdateCollectionUseCase
import javax.inject.Inject

class UpdateCollectionInteractorImpl @Inject constructor(
    private val uploadCollectionCoverUseCase: UploadCollectionCoverUseCase,
    private val updateCollectionUseCase: UpdateCollectionUseCase
) : UpdateCollectionInteractor {
    override suspend fun updateCollection(
        editedCollection: UserCollectionEdit,
        originalCollection: UserCollectionEdit
    ) {
        val imageSource = updateCollectionImageSource(editedCollection, originalCollection)
        val collection = UserCollection(
            id = editedCollection.id,
            name = editedCollection.name,
            description = editedCollection.description,
            imageSource = imageSource,
        )
        updateCollectionUseCase.execute(collection)
    }

    private suspend fun updateCollectionImageSource(
        editedCollection: UserCollectionEdit,
        originalCollection: UserCollectionEdit
    ): String? {
        return when (editedCollection.imageSource) {
            is ImageSourceType.None -> {
                if (originalCollection.imageSource is ImageSourceType.Remote) {

                }
                null
            }

            is ImageSourceType.Remote -> editedCollection.imageSource.source
            is ImageSourceType.Local -> {
                uploadCollectionCoverUseCase.execute(
                    editedCollection.id,
                    editedCollection.imageSource.source
                )
            }
        }
    }
}