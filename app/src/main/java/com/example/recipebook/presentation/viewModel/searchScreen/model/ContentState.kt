package com.example.recipebook.presentation.viewModel.searchScreen.model

import com.example.recipebook.presentation.viewModel.model.RecipeUiState

sealed interface ContentState {
    object Loading: ContentState
    data class SearchContent(val recipeList: List<RecipeUiState>): ContentState
    object Empty: ContentState
    object NothingsFound: ContentState
    object NoInternet: ContentState
    object UnknownError: ContentState
}