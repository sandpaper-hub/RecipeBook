package com.example.recipebook.presentation.viewModel.model

import com.example.recipebook.domain.model.error.validation.ValidationError

data class TimeEstimationUiState(
    val hour: Int = 0,
    val minute: Int = 0,
    val error: ValidationError = ValidationError.None
){
    fun toDisplayString(hourLabel: String, minuteLabel: String): String  =
        buildString {
            when {
                hour == 0 && minute == 0 -> append("")
                hour > 0 && minute > 0 -> append("$hour $hourLabel $minute $minuteLabel")
                hour > 0 -> append("$hour $hourLabel")
                minute > 0 -> append("$minute $minuteLabel")
            }
        }
}
