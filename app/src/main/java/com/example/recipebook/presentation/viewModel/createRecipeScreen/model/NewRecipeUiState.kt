package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import android.net.Uri
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem

data class NewRecipeUiState(
    val recipeImageUri: Uri? = null,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val dropdownMenuItems: List<DropdownMenuItem<MeasureMenuAction>> = listOf(),
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val isCategoryMenuExpand: Boolean = false,
    val errorMessage: String? = null
)