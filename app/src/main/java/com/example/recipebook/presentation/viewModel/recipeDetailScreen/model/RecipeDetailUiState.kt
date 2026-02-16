package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory

data class RecipeDetailUiState(
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val dropdownMenuItems: List<RecipeDetailMenuAction> = listOf(),
    val isOpenDropdownMenu: Boolean = false,
    val isOpenedDeleteDialog: Boolean = false,
    val isShowCollectionSheet: Boolean = false,
    val collectionsUiState: List<CollectionUiState> = listOf(),
    val timeEstimation: String = "",
    val ingredients: List<IngredientUiState> = listOf(),
    val createdAt: Long = 0L
)