package com.example.recipebook.presentation.viewModel.createRecipeScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.interactor.recipes.createNewRecipe.CreateNewRecipeInteractor
import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.validator.RecipeValidator
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.CreateRecipeEvent
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.ImageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val createNewRecipeInteractor: CreateNewRecipeInteractor,
    private val dataValidator: DataValidator,
    private val recipeValidator: RecipeValidator
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewRecipeUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvents = MutableSharedFlow<CreateRecipeEvent>()
    val events = _uiEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    ingredients = listOf(
                        IngredientUiState(
                            id = createRandomIdUseCase.execute(),
                        )
                    ),
                    recipeSteps = listOf(
                        RecipeStepUiState(
                            id = createRandomIdUseCase.execute(),
                        )
                    )
                )
            }
        }
    }

    fun onRecipeImagePicked(uri: Uri?) {
        _uiState.update {
            it.copy(
                recipeImageSource = if (uri == null) {
                    ImageSource.None
                } else {
                    ImageSource.Local(uri.toString())
                }
            )
        }
    }

    fun onRecipeNameChanged(value: String) {
        val nameError = dataValidator.validateStringLength(
            value = value,
            lengthLimit = Constraints.MAX_RECIPE_NAME_LENGTH
        )
        _uiState.update {
            it.copy(
                recipeName = it.recipeName.copy(
                    value = value,
                    error = nameError
                )
            )
        }
    }

    fun setDescription(text: String) {
        val error = dataValidator.validateStringLength(
            value = text,
            lengthLimit = Constraints.MAX_DESCRIPTION_LENGTH
        )

        when (val editTarget = _uiState.value.editTargetDescriptionObject) {
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
                        recipeSteps = it.recipeSteps.map { stepUiState ->
                            if (stepUiState.id == editTarget.stepId) {
                                stepUiState.copy(
                                    description = stepUiState.description.copy(
                                        value = text,
                                        error = error
                                    )
                                )
                            } else stepUiState
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
            it.copy(isTimePickerDialogOpen = isShow)
        }
    }

    fun onTimeEstimationChange(hours: Int, minute: Int) {
        _uiState.update {
            it.copy(
                timeEstimationUiState = it.timeEstimationUiState.copy(
                    hour = hours,
                    minute = minute,
                    error = ValidationError.None
                )
            )
        }
    }

    fun onIngredientChange(editingIngredient: IngredientUiState) {
        val error = dataValidator.validateStringLength(
            value = editingIngredient.value,
            lengthLimit = Constraints.MAX_INGREDIENT_LENGTH
        )

        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                    if (ingredientUiState.id == editingIngredient.id) {
                        ingredientUiState.copy(
                            value = editingIngredient.value,
                            amount = editingIngredient.amount,
                            measure = editingIngredient.measure,
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

    fun setEditTargetObject(editTargetObject: EditTarget?) {
        _uiState.update {
            it.copy(
                editTargetDescriptionObject = editTargetObject,
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
                ingredientList = _uiState.value.ingredients,
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
                _uiEvents.emit(
                    CreateRecipeEvent.MinIngredientCountLimit
                )
            }
        }
    }

    fun addIngredient() {
        if (dataValidator.validateObjectMaxCount(
                ingredientList = _uiState.value.ingredients,
                countLimit = Constraints.MAX_INGREDIENTS
            )
        ) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        ingredients = _uiState.value.ingredients + IngredientUiState(
                            id = createRandomIdUseCase.execute(),
                        )
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _uiEvents.emit(
                    CreateRecipeEvent.MaxIngredientCountLimit
                )
            }
        }
    }

    fun showIngredientDialog(editingIngredient: IngredientUiState?) {
        _uiState.update {
            it.copy(
                editingIngredient = editingIngredient
            )
        }
    }

    fun addStep() {
        viewModelScope.launch {
            if (dataValidator.validateObjectMaxCount(
                    ingredientList = _uiState.value.recipeSteps,
                    countLimit = Constraints.MAX_STEPS
                )
            ) {
                _uiState.update {
                    it.copy(
                        recipeSteps = _uiState.value.recipeSteps + RecipeStepUiState(
                            id = createRandomIdUseCase.execute()
                        )
                    )
                }
            } else {
                _uiEvents.emit(CreateRecipeEvent.MaxStepsCountLimit)
            }
        }
    }

    fun removeStep(id: String) {
        if (dataValidator.validateObjectMinCount(
                ingredientList = _uiState.value.recipeSteps,
                countLimit = Constraints.MIN_STEPS
            )
        ) {
            _uiState.update {
                it.copy(
                    recipeSteps = _uiState.value.recipeSteps.filterNot { recipeStepUiState ->
                        recipeStepUiState.id == id
                    }
                )
            }
        } else {
            viewModelScope.launch {
                _uiEvents.emit(CreateRecipeEvent.MaxStepsCountLimit)
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
                recipeSteps = _uiState.value.recipeSteps.map { recipeStepUiState ->
                    if (recipeStepUiState.id == id) {
                        recipeStepUiState.copy(
                            title = recipeStepUiState.title.copy(
                                value = value,
                                error = titleError
                            )
                        )
                    } else {
                        recipeStepUiState
                    }
                }
            )
        }
    }

    fun onStepImageChange(id: String, uri: Uri?) {
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { recipeStepUiState ->
                    if (recipeStepUiState.id == id) {
                        recipeStepUiState.copy(
                            imageSource = if (uri == null) {
                                ImageSource.None
                            } else {
                                ImageSource.Local(uri.toString())
                            }
                        )
                    } else recipeStepUiState
                })
        }
    }

    fun uploadNewRecipe(onBack: () -> Unit) {
        val (validatedState, isValid) = recipeValidator.validateAll(_uiState.value)
        _uiState.update { validatedState }

        if (!isValid) return
        viewModelScope.launch {
            runCatching {
                createNewRecipeInteractor.invoke(
                    recipeName = validatedState.recipeName.value,
                    recipeDescription = validatedState.description.value,
                    recipeNewTimeEstimation = NewTimeEstimation(
                        hour = validatedState.timeEstimationUiState.hour,
                        minute = validatedState.timeEstimationUiState.minute
                    ),
                    recipeImageSource = validatedState.recipeImageSource.toDomain(),
                    category = validatedState.recipeCategory.value,
                    ingredients = validatedState.ingredients.map { ingredient ->
                        NewRecipeIngredient(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure.toString()
                        )
                    },
                    steps = validatedState.recipeSteps.mapIndexed { index, recipeStepUiState ->
                        UploadRecipeStepDraft(
                            id = recipeStepUiState.id,
                            title = recipeStepUiState.title.value,
                            order = index,
                            imageSource = recipeStepUiState.imageSource.toDomain(),
                            description = recipeStepUiState.description.value
                        )
                    }
                )
            }.onSuccess {
                onBack()
            }.onFailure { error ->


            }
        }
    }
}