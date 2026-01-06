package com.example.recipebook.presentation.viewModel.editProfileScreen

import android.net.Uri

data class EditProfileState(
    val fullName: String = "",
    val nickName: String = "",
    val localImageSource: Uri? = null,
    val profileImageSource: String? = null,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)
