package com.example.recipebook.domain.interactor.recipes.createNewRecipe

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.useCase.recipe.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.recipe.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.recipe.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.recipe.UploadNewRecipeUseCase
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import javax.inject.Inject

class CreateNewRecipeInteractorImpl @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase
) : CreateNewRecipeInteractor{
    override suspend fun invoke(
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<UploadRecipeStepDraft>
    ) {
        val recipeId = createRandomIdUseCase.execute()
        val recipeImageUrl: String? = getRecipeCoverUrlUseCase.execute(recipeId, recipeImageSource)
        val currentUserId = getCurrentUserIdUseCase.execute()
        val recipeSteps = buildRecipeSteps(recipeId, steps)
        uploadNewRecipeUseCase.execute(
            UploadRecipe(
                id = recipeId,
                authorId = currentUserId,
                recipeName = recipeName,
                recipeDescription = recipeDescription,
                recipeTimeEstimation = recipeTimeEstimation,
                imageUrl = recipeImageUrl,
                category = category,
                ingredients = ingredients
            ),
            recipeSteps
        )
    }

    private suspend fun buildRecipeSteps(
        recipeId: String,
        uploadRecipeStepDrafts: List<UploadRecipeStepDraft>
    ): List<UploadRecipeStep> {
        val stepImageUrls = getStepImagesUrlUseCase.execute(
            recipeId = recipeId,
            recipeSteps = uploadRecipeStepDrafts
        )
        return uploadRecipeStepDrafts.map { draft ->
            UploadRecipeStep(
                id = draft.id,
                title = draft.title,
                order = draft.order,
                description = draft.description,
                imageUrl = stepImageUrls[draft.id]
            )
        }
    }
}