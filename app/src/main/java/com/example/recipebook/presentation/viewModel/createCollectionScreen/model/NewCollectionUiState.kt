package com.example.recipebook.presentation.viewModel.createCollectionScreen.model

data class NewCollectionUiState(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageSource: String? = null
)
