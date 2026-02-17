package com.example.recipebook.presentation.viewModel.cookingScreen

data class StepUiState(
    val index: Int = 0,
    val title: String = "",
    val order: Int = 0,
    val imageUrl: String? = null,
    val description: String = ""
)
