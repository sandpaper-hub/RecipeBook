package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.model.Editable
import com.example.recipebook.presentation.viewModel.model.ImageSource
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState

data class EditRecipeUiState(
    val recipeImageSource: ImageSource = ImageSource.None,
    val recipeName: String = "",
    val recipeDescription: Editable.Description = Editable.Description(""),
    val timeEstimationUiState: TimeEstimationUiState = TimeEstimationUiState(),
    val editingIngredient: IngredientUiState? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<EditRecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val editableObject: Editable? = null,
    val isTimeEstimationDialogOpen: Boolean = false,
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false,
    val isMeasureMenuOpen: Boolean = false,
    val errorMessage: String? = null
)
