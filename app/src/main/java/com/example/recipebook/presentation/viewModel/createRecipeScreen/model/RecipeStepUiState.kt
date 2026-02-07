package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import android.net.Uri

data class RecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageUri: Uri? = null,
    val stepDescription: String = ""
)