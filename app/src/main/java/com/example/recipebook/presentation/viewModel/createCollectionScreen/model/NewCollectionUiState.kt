package com.example.recipebook.presentation.viewModel.createCollectionScreen.model

import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField

data class NewCollectionUiState(
    val id: String = "",
    val name: String = "",
    val description: FormField<String> = FormField(""),
    val editTargetObject: EditTarget? = null,
    val imageSource: String? = null
)
