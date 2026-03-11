package com.example.recipebook.presentation.viewModel.collectionEditScreen.model

import com.example.recipebook.presentation.viewModel.model.ImageSource

data class CollectionEditUiState(
    val name: String = "",
    val description: String = "",
    val imageSource: ImageSource = ImageSource.None
)