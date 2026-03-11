package com.example.recipebook.presentation.viewModel.collectionDetailScreen.model

sealed interface CollectionDetailEvent {
    object GoBack: CollectionDetailEvent
    data class OnRecipeDetail(val recipeId: String): CollectionDetailEvent
    data class OnCollectionEdit(val collectionId: String): CollectionDetailEvent
}