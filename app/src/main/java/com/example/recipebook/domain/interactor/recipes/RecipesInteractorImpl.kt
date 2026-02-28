package com.example.recipebook.domain.interactor.recipes

import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.recipe.step.SourceType
import com.example.recipebook.domain.model.recipe.step.Step
import com.example.recipebook.domain.useCase.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.DeleteRecipeUseCase
import com.example.recipebook.domain.useCase.GetCurrentUserIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeByIdUseCase
import com.example.recipebook.domain.useCase.GetRecipeCoverUrlUseCase
import com.example.recipebook.domain.useCase.GetRecipeStepsUseCase
import com.example.recipebook.domain.useCase.GetRecipesByIdsUseCase
import com.example.recipebook.domain.useCase.GetStepImageUrlUseCase
import com.example.recipebook.domain.useCase.GetStepImagesUrlUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.GetUserRecipesUseCase
import com.example.recipebook.domain.useCase.UpdateRecipeUseCase
import com.example.recipebook.domain.useCase.UploadNewRecipeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val getRecipeStepsUseCase: GetRecipeStepsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase,
    private val getStepImageUrlUseCase: GetStepImageUrlUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase
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

    override suspend fun getFullRecipe(recipeId: String): FullRecipe = coroutineScope {
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

    override suspend fun updateRecipe(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ) {
        val recipeImageSource: String? = updateRecipeImageSource(editedRecipe, originalRecipe)
        val recipe = UploadRecipe(
            id = editedRecipe.id,
            authorId = editedRecipe.authorId,
            recipeName = editedRecipe.recipeName,
            recipeDescription = editedRecipe.recipeDescription,
            recipeTimeEstimation = editedRecipe.recipeTimeEstimation,
            imageUrl = recipeImageSource,
            category = editedRecipe.category.name,
            ingredients = editedRecipe.ingredients.map {
                NewRecipeIngredient(
                    id = it.id,
                    value = it.value,
                    amount = it.amount,
                    measure = it.measure.name
                )
            }
        )

        val deleteSteps = buildDeleteSteps(editedRecipe, originalRecipe)
        val updateSteps = buildUpdateSteps(editedRecipe, originalRecipe)
        val addSteps = buildAddSteps(
            editedRecipe = editedRecipe,
            originalRecipe = originalRecipe,
            updateSteps = updateSteps
        )

        updateRecipeUseCase.execute(
            recipe = recipe,
            deleteSteps = deleteSteps,
            updateSteps = updateSteps,
            addSteps = addSteps
        )

    }

    suspend fun updateRecipeImageSource(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ): String? {
        return when (editedRecipe.imageSourceType) {
            is SourceType.None -> {
                if (originalRecipe.imageSourceType == SourceType.None) {
                    null
                } else {
                    //TODO delete image
                    null
                }
            }

            is SourceType.Remote -> editedRecipe.imageSourceType.source
            is SourceType.Local -> {
                getRecipeCoverUrlUseCase.execute(
                    editedRecipe.id,
                    editedRecipe.imageSourceType.source
                )
            }
        }
    }


    suspend fun buildDeleteSteps(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ): List<UploadRecipeStep> {
        val deleteStepIds =
            originalRecipe.steps.map { it.id } - editedRecipe.steps.map { it.id }.toSet()
        //TODO delete old images
        return originalRecipe.steps.filter { it.id in deleteStepIds }.map { step ->
            UploadRecipeStep(
                id = step.id,
                title = step.title,
                description = step.description
            )
        }
    }

    suspend fun buildUpdateSteps(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe
    ): List<UploadRecipeStep> {
        val updateStepIds = editedRecipe.steps.map { it.id }
            .intersect(originalRecipe.steps.map { it.id }.toSet())
        val stepsToUpdate = editedRecipe.steps.filter { it.id in updateStepIds }
        val oldSteps = originalRecipe.steps.filter { it.id in updateStepIds }.associateBy { it.id }

        return stepsToUpdate.mapIndexed { index, step ->
            UploadRecipeStep(
                id = step.id,
                title = step.title,
                order = index,
                description = step.description,
                imageUrl = when (step.sourceType) {
                    is SourceType.None -> {
                        if (oldSteps[step.id]?.sourceType == SourceType.None) {
                            null
                        } else {
                            //TODO delete image
                            null
                        }
                    }

                    is SourceType.Remote -> {
                        step.sourceType.source
                    }

                    is SourceType.Local -> {
                        getStepImageUrlUseCase.execute(
                            recipeId = editedRecipe.id,
                            stepId = step.id,
                            source = step.sourceType.source
                        )
                    }
                }
            )
        }
    }

    suspend fun buildAddSteps(
        editedRecipe: FullRecipe,
        originalRecipe: FullRecipe,
        updateSteps: List<UploadRecipeStep>
    ): List<UploadRecipeStep> {
        val addStepIds =
            editedRecipe.steps.map { it.id }.toSet() - originalRecipe.steps.map { it.id }.toSet()
        val addSteps = editedRecipe.steps.filter { it.id in addStepIds }
        return addSteps.mapIndexed { index, step ->
            UploadRecipeStep(
                id = step.id,
                title = step.title,
                order = updateSteps.size + index,
                description = step.description,
                imageUrl = when (step.sourceType) {
                    is SourceType.Local -> {
                        getStepImageUrlUseCase.execute(
                            recipeId = editedRecipe.id,
                            stepId = step.id,
                            source = step.sourceType.source
                        )
                    }

                    else -> null
                }
            )
        }
    }
}