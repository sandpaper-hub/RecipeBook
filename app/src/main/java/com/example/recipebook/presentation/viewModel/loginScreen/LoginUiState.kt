package com.example.recipebook.presentation.viewModel.loginScreen

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val isLoading: Boolean = false,
    val emailErrorMessageCode: Int? = null,
    val passwordErrorMessageCode: Int? = null,
    val snackBarMessage: String? = null,
    val isSignedIn: Boolean = false
)