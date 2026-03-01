package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class EditRecipeUiState(
    val recipeImageSource: ImageSource = ImageSource.None,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<EditRecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false,
    val errorMessage: String? = null
)
