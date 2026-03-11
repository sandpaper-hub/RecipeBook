package com.example.recipebook.presentation.viewModel.model

sealed interface ImageSource {
    data class Remote(val url: String) : ImageSource
    data class Local(val uri: String) : ImageSource
    object None : ImageSource
}