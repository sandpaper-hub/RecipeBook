package com.example.recipebook.presentation.viewModel.accountScreen

data class AccountUiState(
    val fullName: String = "",
    val region: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val errorMessage: String? = null,
    val isSaving: Boolean = false
)