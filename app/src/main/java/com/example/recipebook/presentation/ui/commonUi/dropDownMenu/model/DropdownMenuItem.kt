package com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model

import androidx.annotation.StringRes

data class DropdownMenuItem<T>(
    val action: T,
    @StringRes val titleResource: Int? = null,
    val title: String? = null
)
