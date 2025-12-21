package com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model

data class NewRecipeUiState(
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList()
)