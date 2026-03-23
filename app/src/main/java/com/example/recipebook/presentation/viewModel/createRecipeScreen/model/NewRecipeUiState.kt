package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable

data class NewRecipeUiState(
    val recipeImageSource: String? = null,
    val recipeName: String = "",
    val description: Editable.Description = Editable.Description(""),
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val editableDescriptionObject: Editable? = null,
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false,
    val errorMessage: String? = null
)