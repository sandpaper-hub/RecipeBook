package com.example.recipebook.presentation.viewModel.collectionsScreen.model

import com.example.recipebook.domain.model.collection.UserCollection

data class CollectionsUiState(
    val collections: List<UserCollection> = emptyList(),
    val isLoading: Boolean = false
)