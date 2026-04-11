package com.example.recipebook.presentation.viewModel.collectionEditScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.interactor.collection.updateCollectionInteractor.UpdateCollectionInteractor
import com.example.recipebook.domain.interactor.validation.DataValidator
import com.example.recipebook.domain.model.collection.UserCollectionEdit
import com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase.GetUserCollectionUseCaseImpl
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailDestination
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.util.toPresentation
import com.example.recipebook.presentation.validator.CollectionValidator
import com.example.recipebook.presentation.viewModel.collectionEditScreen.model.CollectionEditEvent
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CollectionFormUiState
import com.example.recipebook.presentation.viewModel.model.EditTarget
import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionEditViewModel @Inject constructor(
    private val getUserCollectionUseCaseImpl: GetUserCollectionUseCaseImpl,
    private val updateCollectionInteractor: UpdateCollectionInteractor,
    private val dataValidator: DataValidator,
    private val collectionValidator: CollectionValidator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val collectionId =
        checkNotNull(savedStateHandle[CollectionDetailDestination.COLLECTION_ID_ARG]).toString()
    private var originalCollection = UserCollectionEdit()
    private val _event = MutableSharedFlow<CollectionEditEvent>()
    val event = _event.asSharedFlow()
    private val _uiState = MutableStateFlow(CollectionFormUiState())
    val uiState = _uiState.asStateFlow()


    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            originalCollection = getUserCollectionUseCaseImpl.execute(collectionId)
            _uiState.update { collectionEditUiState ->
                collectionEditUiState.copy(
                    name = FormField(value = originalCollection.name),
                    description = FormField(originalCollection.description),
                    imageSource = originalCollection.imageSource.toPresentation()
                )
            }
        }
    }

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

    fun showDescriptionBottomSheet(editTargetObject: EditTarget?) {
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
            is EditTarget.Description -> _uiState.update {
                it.copy(
                    description = it.description.copy(
                        value = text,
                        error = error
                    ),
                    editTargetObject = null
                )
            }

            else -> return
        }
    }

    fun onImageChange(uri: Uri?) {
        _uiState.update {
            it.copy(
                imageSource = if (uri != null) {
                    ImageSource.Local(uri.toString())
                } else ImageSource.None
            )
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _event.emit(CollectionEditEvent.GoBack)
        }
    }

    fun updateCollection() {
        val (validatedState, isValid) = collectionValidator.validateAll(
            state = _uiState.value
        )
        _uiState.update { validatedState }

        if (!isValid) return

        viewModelScope.launch {
            updateCollectionInteractor.updateCollection(
                editedCollection = UserCollectionEdit(
                    id = collectionId,
                    name = _uiState.value.name.value,
                    description = _uiState.value.description.value,
                    imageSource = _uiState.value.imageSource.toDomain()
                ),
                originalCollection = originalCollection
            )
        }
    }
}