package com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor

import com.example.recipebook.domain.useCase.recipe.DeleteRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveBrokenIdUseCase
import javax.inject.Inject

class DeleteRecipeInteractorImpl @Inject constructor(
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val removeBrokenIdUseCase: RemoveBrokenIdUseCase
): DeleteRecipeInteractor {
    override suspend fun invoke(recipeId: String, collectionIds: List<String>) {
        collectionIds.forEach { collectionId ->
            removeBrokenIdUseCase.execute(
                collectionId = collectionId,
                recipeId = recipeId
            )
        }

        deleteRecipeUseCase.execute(recipeId)
    }
}