package com.example.recipebook.domain.interactor.recipes.getRecipeSteps

import com.example.recipebook.domain.model.recipe.step.Step

interface GetRecipeStepsUseCase {
    suspend fun execute(recipeId: String): List<Step>
}