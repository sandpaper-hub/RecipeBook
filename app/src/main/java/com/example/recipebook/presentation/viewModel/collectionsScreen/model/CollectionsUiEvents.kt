package com.example.recipebook.presentation.viewModel.collectionsScreen.model

sealed interface CollectionsUiEvents {
    data class CollectionDetail(val collectionId: String): CollectionsUiEvents
}