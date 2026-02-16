package com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model

import androidx.annotation.StringRes

data class DropdownMenuResourceItem<T>(
    val action: T,
    @StringRes val titleResource: Int
)
