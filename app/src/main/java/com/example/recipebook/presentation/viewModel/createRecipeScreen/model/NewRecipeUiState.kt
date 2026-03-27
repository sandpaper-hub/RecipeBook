package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState

data class NewRecipeUiState(
    val recipeImageSource: String? = null,
    val recipeName: String = "",
    val description: Editable.Description = Editable.Description(""),
    val timeEstimationUiState: TimeEstimationUiState? = TimeEstimationUiState(),
    val editingIngredientId: String? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val editableDescriptionObject: Editable? = null,
    val showTimePickerDialog: Boolean = false,
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false,
    val errorMessage: String? = null
)