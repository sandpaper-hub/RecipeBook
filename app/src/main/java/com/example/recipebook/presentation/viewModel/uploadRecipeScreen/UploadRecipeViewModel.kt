package com.example.recipebook.presentation.viewModel.uploadRecipeScreen

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
                    ingredientValue = ""
                )
            ),
            recipeSteps = listOf(
                RecipeStepUiState(
                    id = UUID.randomUUID().toString(),
                    imageUrl = "",
                    stepDescription = ""
                )
            )
        )
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
            }
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
}