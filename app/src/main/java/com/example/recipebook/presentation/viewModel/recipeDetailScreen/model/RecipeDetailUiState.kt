package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem

data class RecipeDetailUiState(
    val id: String = "",
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val dropdownMenuItems: List<DropdownMenuItem<DropdownMenuAction>> = listOf(),
    val isOpenDropdownMenu: Boolean = false,
    val timeEstimation: String = "",
    val ingredients: List<IngredientUiState> = listOf(),
    val createdAt: Long = 0L
)