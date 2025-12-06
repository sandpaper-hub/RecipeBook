package com.example.recipebook.presentation.viewModel.registrationScreen

sealed class RegistrationUiEvent {
    data class ShowMessage(val message: String?) : RegistrationUiEvent()
}