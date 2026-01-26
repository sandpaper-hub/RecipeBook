package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import android.net.Uri

data class RecipeStepUiState(
    val id: String,
    val imageUri: Uri?,
    val stepDescription: String
)