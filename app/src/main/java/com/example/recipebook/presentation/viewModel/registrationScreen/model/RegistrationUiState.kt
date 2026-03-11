package com.example.recipebook.presentation.viewModel.registrationScreen.model

data class RegistrationUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val isLoading: Boolean = false,
    val emailErrorCode: Int? = null,
    val passwordErrorCode: Int? = null
)