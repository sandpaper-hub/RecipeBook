package com.example.recipebook.presentation.viewModel.searchScreen.model

import com.example.recipebook.domain.model.recipe.getRecipe.Recipe

sealed interface ContentState {
    object Loading: ContentState
    data class SearchContent(val recipeList: List<Recipe>): ContentState
    object Empty: ContentState
    object NothingsFound: ContentState
    object NoInternet: ContentState
    object UnknownError: ContentState
}