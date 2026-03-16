package com.example.recipebook.presentation.viewModel.createCollectionScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.collection.createCollectionInteractor.CreateCollectionInteractor
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CreateCollectionEvent
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.NewCollectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val createCollectionInteractor: CreateCollectionInteractor
) : ViewModel() {
    private val _uiEvents = MutableSharedFlow<CreateCollectionEvent>()
    val uiEvents = _uiEvents.asSharedFlow()
    private val _uiState = MutableStateFlow(NewCollectionUiState())
    val uiState = _uiState.asStateFlow()
    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update {
            it.copy(description = value)
        }
    }

    fun onImageChange(uri: Uri?) {
        _uiState.update {
            it.copy(imageSource = uri?.toString())
        }
    }

    fun createCollection() {
        viewModelScope.launch {
            createCollectionInteractor.createCollection(
                name = uiState.value.name,
                description = uiState.value.description,
                imageSource = uiState.value.imageSource
            )
                .onSuccess {
                    onBack()
                }
                .onFailure {
                    //TODO error
                }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _uiEvents.emit(
                CreateCollectionEvent.GoBack
            )
        }
    }
}