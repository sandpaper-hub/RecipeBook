package com.example.recipebook.presentation.viewModel.createCollectionScreen.model

import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class CollectionFormUiState(
    val id: String = "",
    val name: FormField<String> = FormField(""),
    val description: FormField<String> = FormField(""),
    val editTargetObject: EditTarget? = null,
    val imageSource: ImageSource = ImageSource.None
)
