package com.example.recipebook.presentation.ui.createCollectionScreen.model

import android.net.Uri

data class NewCollectionUiState(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUri: Uri? = null
)
