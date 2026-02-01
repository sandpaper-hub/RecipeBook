package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import com.example.recipebook.domain.model.recipe.RecipeStep

data class RecipeDetailUiState(
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val timeEstimation: String = "",
    val ingredients: List<String> = listOf(),
    val steps: List<RecipeStep> = listOf(),
    val createdAt: Long = 0L
)
