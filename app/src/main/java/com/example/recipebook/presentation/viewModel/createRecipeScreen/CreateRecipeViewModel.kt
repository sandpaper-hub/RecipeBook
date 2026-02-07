package com.example.recipebook.presentation.viewModel.createRecipeScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeIngredient
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStepDraft
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.MeasureMenuAction
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor
) : ViewModel() {
    var uiState by mutableStateOf(NewRecipeUiState())
        private set

    init {
        initDropdownMenuItems()
        viewModelScope.launch {
            uiState = uiState.copy(
                ingredients = listOf(
                    IngredientUiState(
                        id = recipesInteractor.createRandomId(),
                    )
                ),
                recipeSteps = listOf(
                    RecipeStepUiState(
                        id = recipesInteractor.createRandomId(),
                    )
                )
            )
        }
    }

    private fun initDropdownMenuItems() {
        val dropdownMenuItems = listOf(
            DropdownMenuItem(
                action = MeasureMenuAction.TEASPOON,
                titleResource = R.string.measure_teaspoon
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.TABLESPOON,
                titleResource = R.string.measure_tablespoon
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.GRAM,
                titleResource = R.string.measure_g,
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.KILOGRAM,
                titleResource = R.string.measure_kg
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.MILLILITER,
                titleResource = R.string.measure_ml
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.LITER,
                titleResource = R.string.measure_l
            ),
            DropdownMenuItem(
                action = MeasureMenuAction.PCS,
                titleResource = R.string.measure_pcs
            )
        )

        uiState = uiState.copy(
            dropdownMenuItems = dropdownMenuItems
        )
    }


    fun onRecipeImagePicked(uri: Uri?) {
        uiState = uiState.copy(recipeImageUri = uri)
    }

    fun onRecipeNameChanged(value: String) {
        uiState = uiState.copy(recipeName = value)
    }

    fun onRecipeDescriptionChanged(value: String) {
        uiState = uiState.copy(recipeDescription = value)
    }

    fun onRecipeTimeEstimationChanged(value: String) {
        uiState = uiState.copy(timeEstimation = value)
    }

    fun onIngredientChange(
        id: String,
        value: String,
        amount: String,
        measure: MeasureMenuAction
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
                    id = recipesInteractor.createRandomId(),
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
                    id = recipesInteractor.createRandomId(),
                    imageUri = null,
                    stepDescription = ""
                )
            )
        }
    }

    fun removeStep(id: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.filterNot { it.id == id }
        )
    }

    fun onStepDescriptionChange(id: String, value: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(stepDescription = value) else it
            }
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
                if (it.id == id) it.copy(imageUri = uri) else it
            })
    }

    fun uploadNewRecipe(onBack: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                recipesInteractor.uploadNewRecipe(
                    recipeName = uiState.recipeName,
                    recipeDescription = uiState.recipeDescription,
                    recipeTimeEstimation = uiState.timeEstimation,
                    recipeImageSource = uiState.recipeImageUri?.toString(),
                    category = uiState.recipeCategory,
                    ingredients = uiState.ingredients.map { ingredient ->
                        NewRecipeIngredient(
                            id = ingredient.id,
                            value = ingredient.value,
                            amount = ingredient.amount,
                            measure = ingredient.measure.toString()
                        )
                    },
                    steps = uiState.recipeSteps.map { recipeStepUiState ->
                        NewRecipeStepDraft(
                            id = recipeStepUiState.id,
                            title = recipeStepUiState.title,
                            imageSource = recipeStepUiState.imageUri?.toString(),
                            description = recipeStepUiState.stepDescription
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