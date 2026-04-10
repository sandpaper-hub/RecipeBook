package com.example.recipebook.presentation.viewModel.createCollectionScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.interactor.collection.createCollectionInteractor.CreateCollectionInteractor
import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.validator.CollectionValidator
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CreateCollectionEvent
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CollectionFormUiState
import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.ImageSource
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
    private val createCollectionInteractor: CreateCollectionInteractor,
    private val dataValidator: DataValidator,
    private val collectionValidator: CollectionValidator
) : ViewModel() {
    private val _uiEvents = MutableSharedFlow<CreateCollectionEvent>()
    val uiEvents = _uiEvents.asSharedFlow()
    private val _uiState = MutableStateFlow(CollectionFormUiState())
    val uiState = _uiState.asStateFlow()
    fun onNameChange(value: String) {
        val error = dataValidator.validateStringLength(
            value = value,
            lengthLimit = Constraints.MAX_COLLECTION_NAME_LENGTH
        )
        _uiState.update {
            it.copy(
                name = it.name.copy(
                    value = value,
                    error = error
                )
            )
        }
    }

    fun onImageChange(uri: Uri?) {
        _uiState.update {
            it.copy(
                imageSource = if (uri == null) {
                    ImageSource.None
                } else {
                    ImageSource.Local(uri.toString())
                }
            )
        }
    }

    fun setEditTargetObject(editTargetObject: EditTarget.Description?) {
        _uiState.update {
            it.copy(editTargetObject = editTargetObject)
        }
    }

    fun setDescription(text: String) {
        val error = dataValidator.validateStringLength(
            value = text,
            lengthLimit = Constraints.MAX_DESCRIPTION_LENGTH
        )

        when (_uiState.value.editTargetObject) {
            is EditTarget.Description -> {
                _uiState.update {
                    it.copy(
                        description = it.description.copy(
                            value = text,
                            error = error
                        ),
                        editTargetObject = null
                    )
                }
            }

            else -> return
        }
    }

    fun createCollection() {
        val (validatedState, isValid) = collectionValidator.validateAll(_uiState.value)
        _uiState.update { validatedState }
        if (!isValid) return

        viewModelScope.launch {
            createCollectionInteractor.createCollection(
                name = validatedState.name.value,
                description = validatedState.description.value,
                imageSource = validatedState.imageSource.toDomain()
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