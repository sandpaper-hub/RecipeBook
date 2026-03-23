package com.example.recipebook.presentation.viewModel.createCollectionScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable

data class NewCollectionUiState(
    val id: String = "",
    val name: String = "",
    val description: Editable.Description = Editable.Description(""),
    val editableObject: Editable? = null,
    val imageSource: String? = null
)
