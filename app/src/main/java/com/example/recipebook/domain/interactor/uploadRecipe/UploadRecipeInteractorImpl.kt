package com.example.recipebook.domain.interactor.uploadRecipe

import com.example.recipebook.domain.model.NewRecipe
import com.example.recipebook.domain.model.RecipeIngredient
import com.example.recipebook.domain.model.RecipeStep
import com.example.recipebook.domain.model.RecipeStepDraft
import com.example.recipebook.domain.useCase.GetCurrentUserId
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import javax.inject.Inject

class UploadRecipeInteractorImpl @Inject constructor(
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getCurrentUserId: GetCurrentUserId,
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
            NewRecipe(
                id = recipeId,
                authorId = getCurrentUserId.execute(),
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