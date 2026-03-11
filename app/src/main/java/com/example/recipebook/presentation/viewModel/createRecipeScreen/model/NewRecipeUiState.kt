package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

data class NewRecipeUiState(
    val recipeImageSource: String? = null,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val isCategoryMenuExpand: Boolean = false,
    val isEditIngredientDialogOpen: Boolean = false,
    val errorMessage: String? = null
)