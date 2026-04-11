package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState

data class RecipeDetailUiState(
    val isRecipeLoading: Boolean = false,
    val isCollectionsLoading: Boolean = false,
    val recipeErrorMessage: String? = null,
    val collectionsErrorMessage: String? = null,
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val isOpenDropdownMenu: Boolean = false,
    val isOpenedDeleteDialog: Boolean = false,
    val isShowCollectionSheet: Boolean = false,
    val collectionsUiState: List<CollectionUiState> = listOf(),
    val timeEstimationUiState: TimeEstimationUiState = TimeEstimationUiState(),
    val ingredients: List<IngredientUiState> = listOf(),
    val createdAt: Long = 0L
)