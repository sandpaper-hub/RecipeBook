package com.example.recipebook.presentation.viewModel.uploadRecipeScreen.model

import android.net.Uri

data class RecipeStepUiState(
    val id: String,
    val imageUri: Uri?,
    val stepDescription: String
)