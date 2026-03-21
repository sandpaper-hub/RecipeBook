package com.example.recipebook.presentation.viewModel.loginScreen.model

import com.example.recipebook.domain.model.authentication.AuthenticationError

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: AuthenticationError.Email? = null,
    val passwordError: AuthenticationError.Password? = null
)