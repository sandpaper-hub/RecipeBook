package com.example.recipebook.presentation.viewModel.editRecipeScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.FullRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.UpdateRecipeInteractor
import com.example.recipebook.domain.model.recipe.getRecipe.FullRecipe
import com.example.recipebook.domain.model.recipe.getRecipe.Ingredient
import com.example.recipebook.domain.model.recipe.getRecipe.IngredientMeasure
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.recipe.step.SourceType
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailDestination
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeStepUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.ImageSource
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
                    recipeImageSource = when (originalRecipe.imageSourceType) {
                        is SourceType.None -> ImageSource.None
                        is SourceType.Remote -> ImageSource.Remote((originalRecipe.imageSourceType as SourceType.Remote).source)
                        is SourceType.Local -> ImageSource.Local((originalRecipe.imageSourceType as SourceType.Local).source)
                    },
                    recipeName = originalRecipe.recipeName,
                    recipeDescription = originalRecipe.recipeDescription,
                    timeEstimation = originalRecipe.recipeTimeEstimation,
                    ingredients = originalRecipe.ingredients.map { ingredient ->
                        IngredientUiState(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure.name,
                        )
                    },
                    recipeSteps = originalRecipe.steps.map { step ->
                        EditRecipeStepUiState(
                            id = step.id,
                            title = step.title,
                            imageSource = when (step.sourceType) {
                                is SourceType.None -> ImageSource.None
                                is SourceType.Remote -> ImageSource.Remote(step.sourceType.source)
                                is SourceType.Local -> ImageSource.Local(step.sourceType.source)
                            },
                            stepDescription = step.description
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

    fun onRecipeDescriptionChanged(value: String) {
        _uiState.update {
            it.copy(recipeDescription = value)
        }
    }

    fun onRecipeTimeEstimationChanged(value: String) {
        _uiState.update {
            it.copy(timeEstimation = value)
        }
    }

    fun onIngredientChange(
        id: String,
        value: String,
        amount: String,
        measure: String
    ) {
        _uiState.update {
            it.copy(
                ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                    if (ingredientUiState.id == id) {
                        ingredientUiState.copy(
                            value = value,
                            amount = amount,
                            measure = measure
                        )
                    } else ingredientUiState
                },
                editingIngredientId = null
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

    fun showIngredientDialog(id: String?) {
        _uiState.update {
            it.copy(
                editingIngredientId = id
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

    fun onStepDescriptionChange(id: String, value: String) {
        _uiState.update {
            it.copy(
                recipeSteps = _uiState.value.recipeSteps.map { editRecipeStepUiState ->
                    if (editRecipeStepUiState.id == id) {
                        editRecipeStepUiState.copy(stepDescription = value)
                    } else editRecipeStepUiState
                }
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

    fun uploadNewRecipe() {
        viewModelScope.launch {
            runCatching {
                updateRecipeInteractor.updateRecipe(
                    editedRecipe = FullRecipe(
                        id = recipeId,
                        recipeName = _uiState.value.recipeName,
                        recipeDescription = _uiState.value.recipeDescription,
                        recipeTimeEstimation = _uiState.value.timeEstimation,
                        imageSourceType = when (_uiState.value.recipeImageSource) {
                            is ImageSource.None -> SourceType.None
                            is ImageSource.Remote -> SourceType.Remote((_uiState.value.recipeImageSource as ImageSource.Remote).url)
                            is ImageSource.Local -> SourceType.Local((_uiState.value.recipeImageSource as ImageSource.Local).uri)
                        },
                        category = RecipeCategory.from(_uiState.value.recipeCategory),
                        ingredients = _uiState.value.ingredients.map { ingredientUiState ->
                            Ingredient(
                                id = ingredientUiState.id,
                                value = ingredientUiState.value,
                                amount = ingredientUiState.amount,
                                measure = IngredientMeasure.from(ingredientUiState.measure)
                            )
                        },
                        steps = _uiState.value.recipeSteps.mapIndexed { index, stepUiState ->
                            EditStep(
                                id = stepUiState.id,
                                title = stepUiState.title,
                                order = index,
                                description = stepUiState.stepDescription,
                                sourceType = when (stepUiState.imageSource) {
                                    is ImageSource.None -> SourceType.None
                                    is ImageSource.Local -> SourceType.Local(stepUiState.imageSource.uri)
                                    is ImageSource.Remote -> SourceType.Remote(stepUiState.imageSource.url)
                                }
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