package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCaseImpl
import com.example.recipebook.domain.useCase.DeleteRecipeUseCase
import com.example.recipebook.domain.useCase.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeByIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetRecipeStepsUseCase
import com.example.recipebook.domain.useCase.GetRecipesByIdsUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.GetUserRecipesUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesInteractorImpl @Inject constructor(
    private val createRandomIdUseCaseImpl: CreateRandomIdUseCaseImpl,
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val getRecipeStepsUseCase: GetRecipeStepsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase
) : RecipesInteractor {
    override suspend fun getRecipeById(recipeId: String): Recipe {
        return getRecipeByIdUseCase.execute(recipeId)
    }

    override suspend fun createRandomId(): String {
        return createRandomIdUseCaseImpl.execute()
    }

    override suspend fun uploadNewRecipe(
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<UploadRecipeStepDraft>
    ) {
        val recipeId = createRandomIdUseCaseImpl.execute()
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

    override suspend fun buildRecipeSteps(
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

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> =
        getUserRecipesUseCase.execute(userId)

    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCase.execute()

    override suspend fun getRecipeSteps(recipeId: String): List<Step> {
        return getRecipeStepsUseCase.execute(recipeId)
    }

    override suspend fun deleteRecipe(recipeId: String) {
        deleteRecipeUseCase.execute(recipeId)
    }

    override suspend fun getRecipesByIds(
        recipeIds: List<String>
    ): List<Recipe> {
        return getRecipesByIdsUseCase.execute(recipeIds)
    }
}