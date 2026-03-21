package com.example.recipebook.presentation.viewModel.loginScreen.model

sealed interface LoginUiEvent {
    object NetworkError: LoginUiEvent
    object UnknownError: LoginUiEvent
    object OnHomeScreen: LoginUiEvent
}