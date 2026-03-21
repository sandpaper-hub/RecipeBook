package com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor

import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.interactor.recipes.getRecipeSteps.GetRecipeStepsUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.GetRecipeByIdUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FullRecipeInteractorImpl @Inject constructor(
    private val  getRecipeByIdUseCaseImpl: GetRecipeByIdUseCase,
    private val getRecipeStepsUseCaseImpl: GetRecipeStepsUseCaseImpl
) : FullRecipeInteractor {
    override suspend fun getFullRecipe(recipeId: String) = coroutineScope {
        val recipeDeferred = async {
            getRecipeByIdUseCaseImpl.execute(recipeId)
        }

        val stepsDeferred = async {
            getRecipeStepsUseCaseImpl.execute(recipeId)
        }

        val recipe = recipeDeferred.await()
        val steps = stepsDeferred.await()
        FullRecipe(
            id = recipe.id,
            recipeName = recipe.recipeName,
            recipeDescription = recipe.recipeDescription,
            recipeTimeEstimation = recipe.recipeTimeEstimation,
            imageSourceType = if (recipe.imageUrl == null) {
                ImageSourceType.None
            } else ImageSourceType.Remote(recipe.imageUrl),
            category = recipe.category,
            ingredients = recipe.ingredients,
            steps = steps.map { step ->
                EditStep(
                    id = step.id,
                    title = step.title,
                    order = step.order,
                    description = step.description,
                    imageSourceType = if (step.imageSource == null) {
                        ImageSourceType.None
                    } else ImageSourceType.Remote(step.imageSource)
                )
            })
    }
}