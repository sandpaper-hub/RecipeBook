package com.example.recipebook.presentation.viewModel.createCollectionScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.collection.createCollectionInteractor.CreateCollectionInteractor
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.NewCollectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val createCollectionInteractor: CreateCollectionInteractor
) : ViewModel() {
    var uiState by mutableStateOf(NewCollectionUiState())

    fun onNameChange(value: String) {
        uiState = uiState.copy(name = value)
    }

    fun onDescriptionChange(value: String) {
        uiState = uiState.copy(description = value)
    }

    fun onImageChange(uri: Uri?) {
        uiState = uiState.copy(imageSource = uri?.toString())
    }

    fun createCollection(onBack: () -> Unit) {
        viewModelScope.launch {
            createCollectionInteractor.createCollection(
                    name = uiState.name,
                    description = uiState.description,
                    imageSource = uiState.imageSource
            )
                .onSuccess {
                    onBack()
                }
                .onFailure {
                    //TODO error
                }
        }
    }
}