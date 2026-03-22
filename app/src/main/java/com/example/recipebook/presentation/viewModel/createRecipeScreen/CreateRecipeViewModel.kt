package com.example.recipebook.presentation.viewModel.createRecipeScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.createNewRecipe.CreateNewRecipeInteractor
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStepDraft
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
import com.example.recipebook.presentation.viewModel.model.Editable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val createRandomIdUseCase: CreateRandomIdUseCase,
    private val createNewRecipeInteractor: CreateNewRecipeInteractor
) : ViewModel() {
    var uiState by mutableStateOf(NewRecipeUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(
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


    fun onRecipeImagePicked(uri: Uri?) {
        uiState = uiState.copy(
            recipeImageSource = uri?.toString()
        )
    }

    fun onRecipeNameChanged(value: String) {
        uiState = uiState.copy(recipeName = value)
    }

    fun onBottomSheetDescriptionChange(editable: Editable) {
        uiState = uiState.copy(
            editableDescriptionObject = editable
        )
    }

    fun setDescription(editableObject: Editable) {
        uiState = when (editableObject) {
            is Editable.RecipeDescription -> {
                uiState.copy(
                    recipeDescription = editableObject,
                    editableDescriptionObject = null
                )
            }

            is Editable.StepDescription -> {
                uiState.copy(
                    recipeSteps = uiState.recipeSteps.map {
                        if (it.id == editableObject.stepId) {
                            it.copy(stepDescription = editableObject)
                        } else it
                    },
                    editableDescriptionObject = null
                )
            }
        }
    }

    fun onRecipeTimeEstimationChanged(value: String) {
        uiState = uiState.copy(timeEstimation = value)
    }

    fun onIngredientChange(
        id: String,
        value: String,
        amount: String,
        measure: String
    ) {
        uiState = uiState.copy(
            ingredients = uiState.ingredients.map {
                if (it.id == id) it.copy(
                    value = value,
                    amount = amount,
                    measure = measure
                ) else it
            },
            editingIngredientId = null
        )
    }

    fun showCategoryMenu(isShow: Boolean) {
        uiState = uiState.copy(isCategoryMenuExpand = isShow)
    }

    fun showEditBottomSheet(editableObject: Editable?) {
        uiState = uiState.copy(
            editableDescriptionObject = editableObject,
        )
    }

    fun onCategoryChange(value: String) {
        uiState = uiState.copy(
            recipeCategory = value,
            isCategoryMenuExpand = false
        )
    }

    fun removeIngredient(id: String) {
        uiState = uiState.copy(
            ingredients = uiState.ingredients.filterNot { it.id == id }
        )
    }

    fun addIngredient() {
        viewModelScope.launch {
            uiState = uiState.copy(
                ingredients = uiState.ingredients + IngredientUiState(
                    id = createRandomIdUseCase.execute(),
                )
            )
        }
    }

    fun showIngredientDialog(id: String?) {
        uiState = uiState.copy(
            editingIngredientId = id
        )
    }

    fun addStep() {
        viewModelScope.launch {
            uiState = uiState.copy(
                recipeSteps = uiState.recipeSteps + RecipeStepUiState(
                    id = createRandomIdUseCase.execute()
                )
            )
        }
    }

    fun removeStep(id: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.filterNot { it.id == id }
        )
    }

    fun onStepTitleChange(id: String, value: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(title = value) else it
            }
        )
    }

    fun onStepImageChange(id: String, uri: Uri?) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(imageSource = uri?.toString()) else it
            })
    }

    fun uploadNewRecipe(onBack: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                createNewRecipeInteractor.invoke(
                    recipeName = uiState.recipeName,
                    recipeDescription = uiState.recipeDescription.descriptionValue,
                    recipeTimeEstimation = uiState.timeEstimation,
                    recipeImageSource = uiState.recipeImageSource,
                    category = uiState.recipeCategory,
                    ingredients = uiState.ingredients.map { ingredient ->
                        NewRecipeIngredient(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure
                        )
                    },
                    steps = uiState.recipeSteps.mapIndexed { index, recipeStepUiState ->
                        UploadRecipeStepDraft(
                            id = recipeStepUiState.id,
                            title = recipeStepUiState.title,
                            order = index,
                            imageSource = recipeStepUiState.imageSource,
                            description = recipeStepUiState.stepDescription.description
                        )
                    }
                )
            }.onSuccess {
                onBack()
            }.onFailure { error ->
                uiState = uiState.copy(errorMessage = error.message)
            }

        }
    }
}