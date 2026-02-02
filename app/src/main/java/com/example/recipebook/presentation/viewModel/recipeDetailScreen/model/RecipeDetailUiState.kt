package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import com.example.recipebook.domain.model.recipe.RecipeStep
import com.example.recipebook.presentation.ui.model.DropdownMenuItem

data class RecipeDetailUiState(
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val dropdownMenuItems: List<DropdownMenuItem<DropdownMenuAction>> = listOf(),
    val isOpenDropdownMenu: Boolean = false,
    val timeEstimation: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<RecipeStep> = listOf(),
    val createdAt: Long = 0L
)