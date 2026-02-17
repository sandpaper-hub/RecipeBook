package com.example.recipebook.presentation.viewModel.collectionDetailScreen.model

data class CollectionDetailUiState(
    val name: String = "",
    val imageSource: String? = null,
    val description: String = "",
    val collectionSize: Int = 0,
    val recipeIds: List<String> = listOf(),
    val isMenuExpanded: Boolean = false
)
