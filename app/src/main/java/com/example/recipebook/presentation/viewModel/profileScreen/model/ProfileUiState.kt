package com.example.recipebook.presentation.viewModel.profileScreen.model

import android.net.Uri
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe

data class ProfileUiState(
    val fullName: String = "",
    val nickName: String = "",
    val localImageUri: Uri? = null,
    val profileImageUrl: String? = null,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val newRecipes: List<UploadRecipe> = emptyList(),
    val isRecipesLoading: Boolean = false,
    val recipesCount: Int = 0
)