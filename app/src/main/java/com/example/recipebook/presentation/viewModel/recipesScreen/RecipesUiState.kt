package com.example.recipebook.presentation.viewModel.recipesScreen

import com.example.recipebook.domain.model.recipe.Recipe

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList(),
    val isRecipesLoading: Boolean = false
)
