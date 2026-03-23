package com.example.recipebook.presentation.viewModel.model

sealed interface Editable {
    data class Description(val descriptionValue: String): Editable
    data class StepDescription(val stepId: String, val description: String): Editable
}