package com.example.recipebook.presentation.viewModel.registrationScreen.model

interface RegistrationUiEvent {
    object OnHome: RegistrationUiEvent
    object OnLogin: RegistrationUiEvent
    object OnPrivacy: RegistrationUiEvent
    object NetworkError: RegistrationUiEvent
    object UnknownError: RegistrationUiEvent
}