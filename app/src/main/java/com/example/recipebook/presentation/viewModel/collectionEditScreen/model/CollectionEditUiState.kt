package com.example.recipebook.presentation.viewModel.collectionEditScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class CollectionEditUiState(
    val name: String = "",
    val description: Editable.Description = Editable.Description(""),
    val editableObject: Editable? = null,
    val imageSource: ImageSource = ImageSource.None
)