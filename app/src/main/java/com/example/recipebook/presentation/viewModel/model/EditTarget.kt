package com.example.recipebook.presentation.viewModel.model

sealed interface EditTarget {
    data class Description(val descriptionValue: String): EditTarget
    data class StepDescription(val stepId: String, val description: String): EditTarget
}