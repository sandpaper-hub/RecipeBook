package com.example.recipebook.presentation.viewModel.accountScreen

import android.net.Uri
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.MenuStringItem

data class AccountUiState(
    val fullName: String = "",
    val nickName: String = "",
    val region: String = "",
    val regionLocales: List<MenuStringItem<String>> = listOf(),
    val dateOfBirth: Long? = null,
    val localImageSource: Uri? = null,
    val profileImageSource: String? = null,
    val showDatePicker: Boolean = false,
    val showRegionMenu: Boolean = false,
    val gender: String = "",
    val errorMessage: String? = null,
    val isSaving: Boolean = false
)