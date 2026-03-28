package com.example.recipebook.presentation.viewModel.editRecipeScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.updateRecipeInteractor.UpdateRecipeInteractor
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
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeStepUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeUiState
import com.example.recipebook.presentation.viewModel.model.Editable
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId =
        checkNotNull(savedStateHandle[RecipeDetailDestination.RECIPE_ID_ARG]).toString()

    private var _events = MutableSharedFlow<EditRecipeEvent>()
    val events = _events.asSharedFlow()
    private var originalRecipe = FullRecipe()
    private val _uiState = MutableStateFlow(EditRecipeUiState())
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
                    recipeName = originalRecipe.recipeName,
                    recipeDescription = Editable.Description(originalRecipe.recipeDescription),
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
                        EditRecipeStepUiState(
                            id = step.id,
                            title = step.title,
                            imageSource = step.imageSourceType.toPresentation(),
                            stepDescription = Editable.StepDescription(
                                stepId = step.id,
                                description = step.description
                            )
                        )
                    },
                    recipeCategory = originalRecipe.category.name
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
        _uiState.update {
            it.copy(recipeName = value)
        }
    }

    fun setDescription(editable: Editable) {
        when (editable) {
            is Editable.Description -> {
                _uiState.update {
                    it.copy(
                        recipeDescription = editable,
                        editableObject = null
                    )
                }
            }

            is Editable.StepDescription -> {
                _uiState.update {
                    it.copy(
                        recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                            if (editRecipeStepUiState.id == editable.stepId) {
                                editRecipeStepUiState.copy(stepDescription = editable)
                            } else editRecipeStepUiState
                        },
                        editableObject = null
                    )
                }
            }
        }
    }

    fun onEditableObjectChange(editable: Editable?) {
        _uiState.update {
            it.copy(editableObject = editable)
        }
    }

    fun showDescriptionBottomSheet(editable: Editable?) {
        _uiState.update {
            it.copy(
                editableObject = editable
            )
        }
    }

    fun showTimeEstimationDialog(isShow: Boolean) {
        _uiState.update {
            it.copy(
                isTimeEstimationDialogOpen = isShow
            )
        }
    }

    fun showMeasureMenu(isShow: Boolean) {
        _uiState.update {
            it.copy(isMeasureMenuOpen = isShow)
        }
    }

    fun onTimeEstimationChanged(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                timeEstimationUiState = TimeEstimationUiState(hour = hour, minute = minute)
            )
        }
    }

    fun onIngredientChange(
       ingredient: IngredientUiState
    ) {
        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                    if (ingredientUiState.id == ingredient.id) {
                        ingredientUiState.copy(
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure
                        )
                    } else ingredientUiState
                },
                editingIngredient = null
            )
        }
    }

    fun onEditingIngredientChange(ingredient: IngredientUiState) {
        _uiState.update {
            it.copy(
                editingIngredient = ingredient
            )
        }
    }

    fun showCategoryMenu(isShow: Boolean) {
        _uiState.update {
            it.copy(isCategoryMenuExpand = isShow)
        }
    }

    fun onCategoryChange(value: String) {
        _uiState.update {
            it.copy(
                recipeCategory = value,
                isCategoryMenuExpand = false
            )
        }
    }

    fun removeIngredient(id: String) {
        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.filterNot { ingredientUiState ->
                    ingredientUiState.id == id
                }
            )
        }
    }

    fun addIngredient() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    ingredients = _uiState.value.ingredients + IngredientUiState(
                        id = getRandomIdUseCase.execute(),
                    )
                )
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
            _uiState.update {
                it.copy(
                    recipeSteps = _uiState.value.recipeSteps + EditRecipeStepUiState(
                        id = getRandomIdUseCase.execute()
                    )
                )
            }
        }
    }

    fun removeStep(id: String) {
        _uiState.update { editRecipeUiState ->
            editRecipeUiState.copy(
                recipeSteps = _uiState.value.recipeSteps.filterNot { it.id == id }
            )
        }
    }

    fun onStepTitleChange(id: String, value: String) {
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                    if (editRecipeStepUiState.id == id) {
                        editRecipeStepUiState.copy(title = value)
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
        viewModelScope.launch {
            runCatching {
                updateRecipeInteractor.updateRecipe(
                    editedRecipe = FullRecipe(
                        id = recipeId,
                        recipeName = _uiState.value.recipeName,
                        recipeDescription = _uiState.value.recipeDescription.descriptionValue,
                        recipeTimeEstimation = NewTimeEstimation(
                            hour = _uiState.value.timeEstimationUiState.hour,
                            minute = _uiState.value.timeEstimationUiState.minute
                        ),
                        imageSourceType = _uiState.value.recipeImageSource.toDomain(),
                        category = RecipeCategory.from(_uiState.value.recipeCategory),
                        ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                            Ingredient(
                                id = ingredientUiState.id,
                                value = ingredientUiState.value,
                                amount = ingredientUiState.amount,
                                measure = ingredientUiState.measure.name
                            )
                        },
                        steps = _uiState.value.recipeSteps.mapIndexed { index, stepUiState ->
                            EditStep(
                                id = stepUiState.id,
                                title = stepUiState.title,
                                order = index,
                                description = stepUiState.stepDescription.description,
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