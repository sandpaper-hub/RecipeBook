package com.example.recipebook.domain.interactor.uploadRecipe

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStep
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import com.example.recipebook.domain.useCase.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import javax.inject.Inject

class UploadRecipeInteractorImpl @Inject constructor(
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase
) : UploadRecipeInteractor {

    override suspend fun uploadNewRecipe(
        recipeId: String,
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<RecipeIngredient>,
        steps: List<RecipeStepDraft>
    ) {
        uploadNewRecipeUseCase.execute(
            Recipe(
                id = recipeId,
                authorId = getCurrentUserIdUseCase.execute(),
                recipeName = recipeName,
                recipeDescription = recipeDescription,
                recipeTimeEstimation = recipeTimeEstimation,
                imageUrl = getRecipeCoverUrlUseCase.execute(recipeId, recipeImageSource),
                category = category,
                ingredients = ingredients,
                steps = buildRecipeSteps(recipeId, steps),
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun buildRecipeSteps(
        recipeId: String,
        recipeStepDrafts: List<RecipeStepDraft>
    ): List<RecipeStep> {
        val stepImageUrls = getStepImagesUrlUseCase.execute(
            recipeId = recipeId,
            recipeSteps = recipeStepDrafts
        )
        return recipeStepDrafts.map { draft ->
            RecipeStep(
                id = draft.id,
                description = draft.description,
                imageUrl = stepImageUrls[draft.id]
            )
        }
    }
}