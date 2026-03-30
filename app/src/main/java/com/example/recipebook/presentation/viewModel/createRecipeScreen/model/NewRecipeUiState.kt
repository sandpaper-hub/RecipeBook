package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState

data class NewRecipeUiState(
    val recipeImageSource: ImageSource = ImageSource.None,
    val recipeName: FormField<String> = FormField(""),
    val description: FormField<String> = FormField(""),
    val timeEstimationUiState: TimeEstimationUiState? = TimeEstimationUiState(),
    val isMeasureMenuOpen: Boolean = false,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val editingIngredient: IngredientUiState? = null,
    val editTargetDescriptionObject: EditTarget? = null,
    val showTimePickerDialog: Boolean = false,
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false
)