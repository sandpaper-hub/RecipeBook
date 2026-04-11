package com.example.recipebook.presentation.viewModel.collectionDetailScreen.model

import com.example.recipebook.presentation.viewModel.model.RecipeUiState

data class CollectionDetailUiState(
    val name: String = "",
    val imageSource: String? = null,
    val description: String = "",
    val collectionSize: Int = 0,
    val recipeList: List<RecipeUiState> = listOf(),
    val isMenuExpanded: Boolean = false,
    val isDeleteDialogOpen: Boolean = false
)
