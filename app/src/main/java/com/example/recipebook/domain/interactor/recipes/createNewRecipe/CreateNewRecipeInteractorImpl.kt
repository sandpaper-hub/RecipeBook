package com.example.recipebook.domain.interactor.recipes.createNewRecipe

import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.useCase.recipe.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.recipe.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.recipe.UploadNewRecipeUseCase
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.recipe.GetStepImageUrlUseCase
import javax.inject.Inject

class CreateNewRecipeInteractorImpl @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getStepImageUrlUseCase: GetStepImageUrlUseCase
) : CreateNewRecipeInteractor {
    override suspend fun invoke(
        recipeName: String,
        recipeDescription: String,
        recipeNewTimeEstimation: NewTimeEstimation,
        recipeImageSource: ImageSourceType,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<UploadRecipeStepDraft>
    ) {
        val recipeId = createRandomIdUseCase.execute()
        val recipeImageUrl: String? =
            if (recipeImageSource is ImageSourceType.Local) {
                getRecipeCoverUrlUseCase.execute(
                    recipeId,
                    recipeImageSource.source
                )
            } else {
                null
            }
        val currentUserId = getCurrentUserIdUseCase.execute()
        val recipeSteps = buildRecipeSteps(recipeId, steps)
        uploadNewRecipeUseCase.execute(
            UploadRecipe(
                id = recipeId,
                authorId = currentUserId,
                recipeName = recipeName,
                recipeDescription = recipeDescription,
                recipeNewTimeEstimation = recipeNewTimeEstimation,
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
        return uploadRecipeStepDrafts.map { draft ->
            UploadRecipeStep(
                id = draft.id,
                title = draft.title,
                order = draft.order,
                description = draft.description,
                imageUrl = when (draft.imageSource) {
                    is ImageSourceType.Local -> getStepImageUrlUseCase.execute(
                        recipeId = recipeId, stepId = draft.id,
                        source = draft.imageSource.source
                    )

                    else -> null
                }
            )
        }
    }
}