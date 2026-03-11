package com.example.recipebook.presentation.viewModel.searchScreen.model

data class SearchUiState(
    val searchText: String = "",
    val contentState: ContentState = ContentState.Empty
)
