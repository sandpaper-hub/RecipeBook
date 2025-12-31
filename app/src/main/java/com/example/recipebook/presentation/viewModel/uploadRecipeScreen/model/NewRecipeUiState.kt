package com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model

import android.net.Uri

data class NewRecipeUiState(
    val recipeImageUri: Uri? = null,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: String = "",
    val isCategoryMenuExpand: Boolean = false
)