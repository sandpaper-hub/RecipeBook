package com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor

import com.example.recipebook.domain.useCase.collection.getCollectionIdsByRecipeUseCase.GetCollectionIdsByRecipeUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.deleteRecipe.DeleteRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveRecipeFromCollectionUseCase
import javax.inject.Inject

class DeleteRecipeInteractorImpl @Inject constructor(
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getCollectionIdsByRecipeUseCaseImpl: GetCollectionIdsByRecipeUseCaseImpl,
    private val removeRecipeFromCollectionUseCase: RemoveRecipeFromCollectionUseCase
): DeleteRecipeInteractor {
    override suspend fun invoke(recipeId: String) {
        val collectionIds = getCollectionIdsByRecipeUseCaseImpl.execute(recipeId)

        collectionIds.forEach { collectionId ->
            removeRecipeFromCollectionUseCase.execute(
                collectionId = collectionId,
                recipeId = recipeId
            )
        }

        deleteRecipeUseCase.execute(recipeId)
    }
}