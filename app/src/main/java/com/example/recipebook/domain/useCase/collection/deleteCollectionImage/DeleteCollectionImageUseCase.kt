package com.example.recipebook.domain.useCase.collection.deleteCollectionImage

import com.example.recipebook.domain.repository.DeleteImageRepository
import javax.inject.Inject

class DeleteCollectionImageUseCase @Inject constructor(
    private val deleteImageRepository: DeleteImageRepository
) {
    suspend fun execute(collectionId: String){
        deleteImageRepository.deleteCollectionImage(collectionId)
    }
}