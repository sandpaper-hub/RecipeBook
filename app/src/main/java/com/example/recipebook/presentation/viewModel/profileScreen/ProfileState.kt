package com.example.recipebook.presentation.viewModel.profileScreen

import android.net.Uri
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipe

data class ProfileState(
    val fullName: String = "",
    val nickName: String = "",
    val localImageUri: Uri? = null,
    val profileImageUrl: String? = null,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val newRecipes: List<NewRecipe> = emptyList(),
    val isRecipesLoading: Boolean = false,
    val recipesCount: Int = 0
)