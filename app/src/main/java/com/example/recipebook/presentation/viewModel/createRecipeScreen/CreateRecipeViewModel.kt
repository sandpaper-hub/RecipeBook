package com.example.recipebook.presentation.viewModel.createRecipeScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.createNewRecipe.CreateNewRecipeInteractor
import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.CreateRecipeEvent
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
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
class CreateRecipeViewModel @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val createNewRecipeInteractor: CreateNewRecipeInteractor,
    private val dataValidator: DataValidator
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
        val nameError = dataValidator.validateStringLength(value, 100)
        _uiState.update {
            it.copy(
                recipeName = FormField(
                    value = value,
                    error = nameError
                )
            )
        }
    }

    fun setDescription(text: String) {
        val error = dataValidator.validateStringLength(text, 1500)

        when (val editTarget = _uiState.value.editTargetDescriptionObject) {
            is EditTarget.Description -> {
                _uiState.update {
                    it.copy(
                        description = FormField(
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
                                    stepDescription = FormField(
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
                timeEstimationUiState = TimeEstimationUiState(hour = hours, minute = minute)
            )
        }
    }

    fun onIngredientChange(editingIngredient: IngredientUiState) {
        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                    if (ingredientUiState.id == editingIngredient.id) {
                        ingredientUiState.copy(
                            value = editingIngredient.value,
                            amount = editingIngredient.amount,
                            measure = editingIngredient.measure
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
                recipeCategory = FormField(value = value),
                isCategoryMenuExpand = false
            )
        }
    }

    fun removeIngredient(id: String) {
        if (dataValidator.validateIngredientMinCount(_uiState.value.ingredients)) {
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
        if (dataValidator.validateIngredientMaxCount(_uiState.value.ingredients)) {
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
            _uiState.update {
                it.copy(
                    recipeSteps = _uiState.value.recipeSteps + RecipeStepUiState(
                        id = createRandomIdUseCase.execute()
                    )
                )
            }
        }
    }

    fun removeStep(id: String) {
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.filterNot { recipeStepUiState ->
                    recipeStepUiState.id == id
                }
            )
        }
    }

    fun onStepTitleChange(id: String, value: String) {
        val titleError = dataValidator.validateStringLength(value = value, lengthLimit = 70)
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { recipeStepUiState ->
                    if (recipeStepUiState.id == id) {
                        recipeStepUiState.copy(
                            title = FormField(
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
                        recipeStepUiState.copy(imageSource = uri?.toString())
                    } else recipeStepUiState
                })
        }
    }

    fun uploadNewRecipe(onBack: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                createNewRecipeInteractor.invoke(
                    recipeName = _uiState.value.recipeName.value,
                    recipeDescription = _uiState.value.description.value,
                    recipeNewTimeEstimation = NewTimeEstimation(
                        hour = _uiState.value.timeEstimationUiState?.hour ?: 0,
                        minute = _uiState.value.timeEstimationUiState?.minute ?: 0
                    ),
                    recipeImageSource = _uiState.value.recipeImageSource.toDomain(),
                    category = _uiState.value.recipeCategory.value,
                    ingredients = _uiState.value.ingredients.map { ingredient ->
                        NewRecipeIngredient(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure.toString()
                        )
                    },
                    steps = _uiState.value.recipeSteps.mapIndexed { index, recipeStepUiState ->
                        UploadRecipeStepDraft(
                            id = recipeStepUiState.id,
                            title = recipeStepUiState.title.value,
                            order = index,
                            imageSource = recipeStepUiState.imageSource,
                            description = recipeStepUiState.stepDescription.value
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