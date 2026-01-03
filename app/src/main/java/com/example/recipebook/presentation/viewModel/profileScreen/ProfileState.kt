package com.example.recipebook.presentation.viewModel.profileScreen

import android.net.Uri
import com.example.recipebook.domain.model.recipe.Recipe

data class ProfileState(
    val fullName: String = "",
    val nickName: String = "",
    val localImageUri: Uri? = null,
    val profileImageUrl: String? = null,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val recipes: List<Recipe> = emptyList(),
    val isRecipesLoading: Boolean = false
)