package com.example.recipebook.presentation.viewModel.collectionEditScreen.model

sealed interface CollectionEditEvent {
    object GoBack : CollectionEditEvent
}