package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

data class CollectionUiState(
    val id: String = "",
    val name: String = "",
    val imageUrl: String? = null,
    val containRecipe: Boolean = false,
    val isUpdating: Boolean = false
)
