package com.example.recipebook.presentation.viewModel.accountScreen

import android.net.Uri

data class AccountUiState(
    val fullName: String = "",
    val nickName: String = "",
    val region: String = "",
    val regionLocales: List<String> = listOf(),
    val dateOfBirth: Long? = null,
    val localImageSource: Uri? = null,
    val profileImageSource: String? = null,
    val showDatePicker: Boolean = false,
    val showRegionMenu: Boolean = false,
    val gender: String = "",
    val errorMessage: String? = null,
    val isSaving: Boolean = false
)