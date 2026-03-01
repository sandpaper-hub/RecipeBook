package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.recipe.step.SourceType
import com.example.recipebook.domain.useCase.GetRecipeByIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeStepsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FullRecipeInteractorImpl @Inject constructor(
    private val  getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val getRecipeStepsUseCase: GetRecipeStepsUseCase
) : FullRecipeInteractor {
    override suspend fun getFullRecipe(recipeId: String) = coroutineScope {
        val recipeDeferred = async {
            getRecipeByIdUseCase.execute(recipeId)
        }

        val stepsDeferred = async {
            getRecipeStepsUseCase.execute(recipeId)
        }

        val recipe = recipeDeferred.await()
        val steps = stepsDeferred.await()
        FullRecipe(
            id = recipe.id,
            recipeName = recipe.recipeName,
            recipeDescription = recipe.recipeDescription,
            recipeTimeEstimation = recipe.recipeTimeEstimation,
            imageSourceType = if (recipe.imageUrl == null) {
                SourceType.None
            } else SourceType.Remote(recipe.imageUrl),
            category = recipe.category,
            ingredients = recipe.ingredients,
            steps = steps.map { step ->
                EditStep(
                    id = step.id,
                    title = step.title,
                    order = step.order,
                    description = step.description,
                    sourceType = if (step.imageSource == null) {
                        SourceType.None
                    } else SourceType.Remote(step.imageSource)
                )
            })
    }
}