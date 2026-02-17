package com.example.recipebook.presentation.viewModel.collectionDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailDestination
import com.example.recipebook.presentation.viewModel.collectionDetailScreen.model.CollectionDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class
CollectionDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val collectionId =
        checkNotNull(savedStateHandle[CollectionDetailDestination.COLLECTION_ID_ARG])
    private val _uiState = MutableStateFlow(CollectionDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun expandMenu(isExpand: Boolean) {
        _uiState.update {
            it.copy(isMenuExpanded = isExpand)
        }
    }
}