package com.example.recipebook.presentation.viewModel.createCollectionScreen.model

sealed interface CreateCollectionEvent {
    object GoBack: CreateCollectionEvent
}