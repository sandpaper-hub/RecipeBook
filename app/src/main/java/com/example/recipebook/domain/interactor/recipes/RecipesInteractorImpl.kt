package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.useCase.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeByIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.GetUserRecipesUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesInteractorImpl @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : RecipesInteractor {
    override suspend fun getRecipeById(recipeId: String): Recipe {
        return getRecipeByIdUseCase.execute(recipeId)
    }

    override suspend fun createRandomId(): String {
        return createRandomIdUseCase.execute()
    }

    override suspend fun uploadNewRecipe(
        recipeName: String,
        recipeDescription: String,
        recipeTimeEstimation: String,
        recipeImageSource: String?,
        category: String,
        ingredients: List<NewRecipeIngredient>,
        steps: List<NewRecipeStepDraft>
    ) {
        val recipeId = createRandomIdUseCase.execute()
        val recipeImageUrl: String? = getRecipeCoverUrlUseCase.execute(recipeId, recipeImageSource)
        val currentUserId = getCurrentUserIdUseCase.execute()
        val recipeSteps = buildRecipeSteps(recipeId, steps)
        uploadNewRecipeUseCase.execute(
            NewRecipe(
                id = recipeId,
                authorId = currentUserId,
                recipeName = recipeName,
                recipeDescription = recipeDescription,
                recipeTimeEstimation = recipeTimeEstimation,
                imageUrl = recipeImageUrl,
                category = category,
                ingredients = ingredients,
                steps = recipeSteps
            )
        )
    }

    override suspend fun buildRecipeSteps(
        recipeId: String,
        newRecipeStepDrafts: List<NewRecipeStepDraft>
    ): List<NewRecipeStep> {
        val stepImageUrls = getStepImagesUrlUseCase.execute(
            recipeId = recipeId,
            recipeSteps = newRecipeStepDrafts
        )
        return newRecipeStepDrafts.map { draft ->
            NewRecipeStep(
                id = draft.id,
                description = draft.description,
                imageUrl = stepImageUrls[draft.id]
            )
        }
    }

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> =
        getUserRecipesUseCase.execute(userId)

    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCase.execute()
}