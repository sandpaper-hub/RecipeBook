package com.example.recipebook.presentation.viewModel.createCollectionScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipebook.presentation.ui.createCollectionScreen.model.NewCollectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
) : ViewModel() {
    var uiState by mutableStateOf(NewCollectionUiState())

    fun onNameChange(value: String) {
        uiState = uiState.copy(name = value)
    }

    fun onDescriptionChange(value: String) {
        uiState = uiState.copy(description = value)
    }

    fun onImageChange(uri: Uri?) {
        uiState = uiState.copy(imageUri = uri)
    }
}