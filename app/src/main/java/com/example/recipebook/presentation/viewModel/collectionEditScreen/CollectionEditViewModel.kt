package com.example.recipebook.presentation.viewModel.collectionEditScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.model.collection.UserCollectionEdit
import com.example.recipebook.domain.model.recipe.step.ImageSourceType
import com.example.recipebook.domain.useCase.getUserCollection.GetUserCollectionUseCaseImpl
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailDestination
import com.example.recipebook.presentation.viewModel.collectionEditScreen.model.CollectionEditUiState
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val collectionId =
        checkNotNull(savedStateHandle[CollectionDetailDestination.COLLECTION_ID_ARG]).toString()
    private var oldCollection = UserCollectionEdit()
    private val _event = MutableSharedFlow<EditRecipeEvent>()
    val event = _event.asSharedFlow()
    private val _uiState = MutableStateFlow(CollectionEditUiState())
    val uiState = _uiState.asStateFlow()


    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            oldCollection = getUserCollectionUseCaseImpl.execute(collectionId)
            _uiState.update { collectionEditUiState ->
                collectionEditUiState.copy(
                    name = oldCollection.name,
                    description = oldCollection.description,
                    imageSource = when (oldCollection.imageSource) {
                        is ImageSourceType.None -> ImageSource.None
                        is ImageSourceType.Remote -> {
                            ImageSource.Remote(
                                (oldCollection.imageSource as ImageSourceType.Remote).source
                            )
                        }

                        is ImageSourceType.Local -> {
                            ImageSource.Local(
                                (oldCollection.imageSource as ImageSourceType.Local).source
                            )
                        }
                    }
                )
            }
        }
    }

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
            it.copy(
                imageSource = if (uri != null) {
                    ImageSource.Local(uri.toString())
                } else ImageSource.None
            )
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _event.emit(EditRecipeEvent.GoBack)
        }
    }

    fun updateCollection() {

    }
}