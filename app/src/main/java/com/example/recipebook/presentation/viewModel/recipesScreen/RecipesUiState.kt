package com.example.recipebook.presentation.viewModel.recipesScreen

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe

data class RecipesUiState(
    val newRecipes: List<Recipe> = emptyList(),
    val isRecipesLoading: Boolean = false
)
