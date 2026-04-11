package com.example.recipebook.presentation.viewModel.editRecipeScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.updateRecipeInteractor.UpdateRecipeInteractor
import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.getRecipe.Ingredient
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailDestination
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.util.toPresentation
import com.example.recipebook.presentation.validator.RecipeValidator
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeFormUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent
import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor(
    private val updateRecipeInteractor: UpdateRecipeInteractor,
    private val getRandomIdUseCase: CreateRandomIdUseCase,
    private val fullRecipeInteractor: FullRecipeInteractor,
    private val recipeValidator: RecipeValidator,
    private val dataValidator: DataValidator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId =
        checkNotNull(savedStateHandle[RecipeDetailDestination.RECIPE_ID_ARG]).toString()

    private var _events = MutableSharedFlow<EditRecipeEvent>()
    val events = _events.asSharedFlow()
    private var originalRecipe = FullRecipe()
    private val _uiState = MutableStateFlow(RecipeFormUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFullRecipe()
    }

    private fun getFullRecipe() {
        viewModelScope.launch {
            originalRecipe = fullRecipeInteractor.getFullRecipe(recipeId)
            _uiState.update { editRecipeUiState ->
                editRecipeUiState.copy(
                    recipeImageSource = originalRecipe.imageSourceType.toPresentation(),
                    recipeName = FormField(value = originalRecipe.recipeName),
                    description = FormField(originalRecipe.recipeDescription),
                    timeEstimationUiState = TimeEstimationUiState(
                        hour = originalRecipe.recipeTimeEstimation.hour,
                        minute = originalRecipe.recipeTimeEstimation.minute
                    ),
                    ingredients = originalRecipe.ingredients.map { ingredient ->
                        IngredientUiState(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = MeasureMenuItem.from(ingredient.measure),
                        )
                    },
                    recipeSteps = originalRecipe.steps.map { step ->
                        RecipeStepUiState(
                            id = step.id,
                            title = FormField(step.title),
                            imageSource = step.imageSourceType.toPresentation(),
                            description = FormField(step.description)
                        )
                    },
                    recipeCategory = FormField(originalRecipe.category.name)
                )
            }
        }
    }

    fun onRecipeImagePicked(uri: Uri?) {
        _uiState.update {
            it.copy(
                recipeImageSource = if (uri == null) {
                    ImageSource.None
                } else ImageSource.Local(uri.toString())
            )
        }
    }

    fun onRecipeNameChanged(value: String) {
        val error = dataValidator.validateStringLength(
            value = value,
            lengthLimit = Constraints.MAX_RECIPE_NAME_LENGTH
        )

        _uiState.update {
            it.copy(
                recipeName = it.recipeName.copy(
                    value = value,
                    error = error
                )
            )
        }
    }

    fun setDescription(text: String) {
        val error = dataValidator.validateStringLength(
            value = text,
            lengthLimit = Constraints.MAX_DESCRIPTION_LENGTH
        )

        when (val target = _uiState.value.editTargetDescriptionObject) {
            is EditTarget.Description -> {
                _uiState.update {
                    it.copy(
                        description = it.description.copy(
                            value = text,
                            error = error
                        ),
                        editTargetDescriptionObject = null
                    )
                }
            }

            is EditTarget.StepDescription -> {
                _uiState.update {
                    it.copy(
                        recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                            if (editRecipeStepUiState.id == target.stepId) {
                                editRecipeStepUiState.copy(
                                    description = editRecipeStepUiState.description.copy(
                                        value = text,
                                        error = error
                                    )
                                )
                            } else editRecipeStepUiState
                        },
                        editTargetDescriptionObject = null
                    )
                }
            }

            else -> {
                return
            }
        }
    }

    fun showTimePickerDialog(isShow: Boolean) {
        _uiState.update {
            it.copy(
                isTimePickerDialogOpen = isShow
            )
        }
    }

    fun onTimeEstimationChanged(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                timeEstimationUiState = it.timeEstimationUiState.copy(
                    hour = hour,
                    minute = minute,
                    error = ValidationError.None
                )
            )
        }
    }

    fun onIngredientChange(ingredient: IngredientUiState) {
        val error = dataValidator.validateStringLength(
            value = ingredient.value,
            lengthLimit = Constraints.MAX_INGREDIENT_LENGTH
        )

        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                    if (ingredientUiState.id == ingredient.id) {
                        ingredientUiState.copy(
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure,
                            error = error
                        )
                    } else ingredientUiState
                },
                editingIngredient = null
            )
        }
    }

    fun showCategoryMenu(isShow: Boolean) {
        _uiState.update {
            it.copy(isCategoryMenuExpand = isShow)
        }
    }

    fun setEditTargetObject(editTarget: EditTarget?) {
        _uiState.update {
            it.copy(
                editTargetDescriptionObject = editTarget
            )
        }
    }

    fun onCategoryChange(value: String) {
        _uiState.update {
            it.copy(
                recipeCategory = it.recipeCategory.copy(value = value),
                isCategoryMenuExpand = false
            )
        }
    }

    fun removeIngredient(id: String) {
        if (dataValidator.validateObjectMinCount(
                objectsList = _uiState.value.ingredients,
                countLimit = Constraints.MIN_INGREDIENTS
            )
        ) {
            _uiState.update {
                it.copy(
                    ingredients = _uiState.value.ingredients.filterNot { ingredientUiState ->
                        ingredientUiState.id == id
                    }
                )
            }
        } else {
            viewModelScope.launch {
                _events.emit(EditRecipeEvent.MinIngredientCountLimit)
            }
        }
    }

    fun addIngredient() {
        if (dataValidator.validateObjectMaxCount(
                objectsList = _uiState.value.ingredients,
                countLimit = Constraints.MAX_INGREDIENTS
            )
        ) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        ingredients = _uiState.value.ingredients + IngredientUiState(
                            id = getRandomIdUseCase.execute(),
                        )
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _events.emit(EditRecipeEvent.MaxIngredientCountLimit)
            }
        }
    }

    fun showIngredientDialog(ingredient: IngredientUiState?) {
        _uiState.update {
            it.copy(
                editingIngredient = ingredient
            )
        }
    }

    fun addStep() {
        viewModelScope.launch {
            if (dataValidator.validateObjectMaxCount(
                    objectsList = _uiState.value.recipeSteps,
                    countLimit = Constraints.MAX_STEPS
                )
            ) {
                _uiState.update {
                    it.copy(
                        recipeSteps = _uiState.value.recipeSteps + RecipeStepUiState(
                            id = getRandomIdUseCase.execute()
                        )
                    )
                }
            } else {
                _events.emit(EditRecipeEvent.MaxStepsCountLimit)
            }
        }
    }

    fun removeStep(id: String) {
        if (dataValidator.validateObjectMinCount(
                objectsList = _uiState.value.recipeSteps,
                countLimit = Constraints.MIN_STEPS
            )
        ) {
            _uiState.update { editRecipeUiState ->
                editRecipeUiState.copy(
                    recipeSteps = _uiState.value.recipeSteps.filterNot { it.id == id }
                )
            }
        } else {
            viewModelScope.launch {
                _events.emit(EditRecipeEvent.MinStepsCountLimit)
            }
        }
    }

    fun onStepTitleChange(id: String, value: String) {
        val titleError = dataValidator.validateStringLength(
            value = value,
            lengthLimit = Constraints.MAX_STEP_TITLE_LENGTH
        )

        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                    if (editRecipeStepUiState.id == id) {
                        editRecipeStepUiState.copy(
                            title = editRecipeStepUiState.title.copy(
                                value = value,
                                error = titleError
                            )
                        )
                    } else editRecipeStepUiState
                }
            )
        }
    }

    fun onStepImageChange(id: String, uri: Uri?) {
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                    if (editRecipeStepUiState.id == id) {
                        editRecipeStepUiState.copy(
                            imageSource = if (uri == null) {
                                ImageSource.None
                            } else ImageSource.Local(uri.toString())
                        )
                    } else editRecipeStepUiState
                })
        }
    }

    fun updateRecipe() {
        val (validatedState, isValid) = recipeValidator.validateAll(_uiState.value)
        _uiState.update { validatedState }

        if (!isValid) return

        viewModelScope.launch {
            runCatching {
                updateRecipeInteractor.updateRecipe(
                    editedRecipe = FullRecipe(
                        id = recipeId,
                        recipeName = validatedState.recipeName.value,
                        recipeDescription = validatedState.description.value,
                        recipeTimeEstimation = NewTimeEstimation(
                            hour = validatedState.timeEstimationUiState.hour,
                            minute = validatedState.timeEstimationUiState.minute
                        ),
                        imageSourceType = validatedState.recipeImageSource.toDomain(),
                        category = RecipeCategory.from(validatedState.recipeCategory.value),
                        ingredients = validatedState.ingredients.map { ingredientUiState ->
                            Ingredient(
                                id = ingredientUiState.id,
                                value = ingredientUiState.value,
                                amount = ingredientUiState.amount,
                                measure = ingredientUiState.measure.name
                            )
                        },
                        steps = validatedState.recipeSteps.mapIndexed { index, stepUiState ->
                            EditStep(
                                id = stepUiState.id,
                                title = stepUiState.title.value,
                                order = index,
                                description = stepUiState.description.value,
                                imageSourceType = stepUiState.imageSource.toDomain()
                            )
                        }
                    ),
                    originalRecipe = originalRecipe)
            }
                .onSuccess { goBack() }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            _events.emit(EditRecipeEvent.GoBack)
        }
    }
}