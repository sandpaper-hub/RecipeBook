package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStep
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import com.example.recipebook.domain.useCase.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.GetUserRecipesUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesInteractorImpl @Inject constructor(
    private val getStepImagesUrlUseCase: GetStepImagesUrlUseCase,
    private val uploadNewRecipeUseCase: UploadNewRecipeUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getRecipeCoverUrlUseCase: GetRecipeCoverUrlUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase
) : RecipesInteractor {

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
        val recipeImageUrl: String? = getRecipeCoverUrlUseCase.execute(recipeId, recipeImageSource)
        val currentUserId = getCurrentUserIdUseCase.execute()
        val recipeSteps = buildRecipeSteps(recipeId, steps)
        uploadNewRecipeUseCase.execute(
            Recipe(
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

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> =
        getUserRecipesUseCase.execute(userId)

    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCase.execute()
}