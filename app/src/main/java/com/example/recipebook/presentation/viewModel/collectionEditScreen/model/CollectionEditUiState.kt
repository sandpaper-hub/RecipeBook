package com.example.recipebook.presentation.viewModel.collectionEditScreen.model

import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class CollectionEditUiState(
    val name: String = "",
    val description: FormField<String> = FormField(""),
    val editTargetObject: EditTarget? = null,
    val imageSource: ImageSource = ImageSource.None
)