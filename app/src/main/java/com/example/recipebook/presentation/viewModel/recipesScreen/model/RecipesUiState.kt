package com.example.recipebook.presentation.viewModel.recipesScreen.model

import com.example.recipebook.presentation.viewModel.model.RecipeUiState

data class RecipesUiState(
    val recipes: List<RecipeUiState> = emptyList(),
    val isRecipesLoading: Boolean = false
)