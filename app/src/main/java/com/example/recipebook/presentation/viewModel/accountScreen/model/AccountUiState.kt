package com.example.recipebook.presentation.viewModel.accountScreen.model

import com.example.recipebook.presentation.viewModel.model.ImageSource

data class AccountUiState(
    val fullName: String = "",
    val nickName: String = "",
    val region: String = "",
    val regionLocales: List<String> = listOf(),
    val dateOfBirth: Long? = null,
    val profileImageSource: ImageSource = ImageSource.None,
    val showDatePicker: Boolean = false,
    val showRegionMenu: Boolean = false,
    val gender: String = "",
    val errorMessage: String? = null,
    val isSaving: Boolean = false
)