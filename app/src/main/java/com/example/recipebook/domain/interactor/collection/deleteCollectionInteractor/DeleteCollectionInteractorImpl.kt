package com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor

import com.example.recipebook.domain.useCase.collection.DeleteCollectionUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveBrokenIdUseCase
import javax.inject.Inject

class DeleteCollectionInteractorImpl @Inject constructor(
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val removeBrokenIdUseCase: RemoveBrokenIdUseCase
) : DeleteCollectionInteractor {
    override suspend fun invoke(
        collectionId: String,
        recipeIds: List<String>
    ) {
        recipeIds.forEach { recipeId ->
            removeBrokenIdUseCase.execute(
                collectionId = collectionId,
                recipeId = recipeId
            )
        }

        deleteCollectionUseCase.execute(collectionId)
    }
}