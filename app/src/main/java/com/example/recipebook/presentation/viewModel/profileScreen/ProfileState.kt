package com.example.recipebook.presentation.viewModel.profileScreen

import android.net.Uri

data class ProfileState(
    val uid: String = "",
    val fullName: String = "",
    val nickName: String = "",
    val localImageUri: Uri? = null,
    val remoteImageUrl: String? = null,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)