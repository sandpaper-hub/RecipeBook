package com.example.recipebook.presentation.viewModel.model

sealed class UiEvent {
    data class ShowMessage(val message: String?) : UiEvent()
}