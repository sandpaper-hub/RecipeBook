package com.example.recipebook.presentation.viewModel.uploadRecipeScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model.RecipeStepUiState
import java.util.UUID
import javax.inject.Inject

class UploadRecipeViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(NewRecipeUiState())
        private set

    init {
        uiState = uiState.copy(
            ingredients = listOf(
                IngredientUiState(
                    id = UUID.randomUUID().toString(),
                    ingredientValue = "",
                )
            ),
            recipeSteps = listOf(
                RecipeStepUiState(
                    id = UUID.randomUUID().toString(),
                    imageUrl = null,
                    stepDescription = ""
                )
            )
        )
    }

    fun onRecipeImagePicked(uri: Uri?) {
        uiState = uiState.copy(recipeImageUrl = uri)
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

    fun onIngredientChange(id: String, value: String) {
        uiState = uiState.copy(
            ingredients = uiState.ingredients.map {
                if (it.id == id) it.copy(ingredientValue = value) else it
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
        uiState = uiState.copy(
            ingredients = uiState.ingredients + IngredientUiState(
                id = UUID.randomUUID().toString(),
                ingredientValue = ""
            )
        )
    }

    fun showIngredientDialog(id: String?) {
        uiState = uiState.copy(
            editingIngredientId = id
        )
    }

    fun addStep() {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps + RecipeStepUiState(
                id = UUID.randomUUID().toString(),
                imageUrl = null,
                stepDescription = ""
            )
        )
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

    fun onStepImageChange(id: String, uri: Uri?) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(imageUrl = uri) else it
            })
    }
}