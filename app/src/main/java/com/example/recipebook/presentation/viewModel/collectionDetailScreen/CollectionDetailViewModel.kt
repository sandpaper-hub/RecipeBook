package com.example.recipebook.presentation.viewModel.collectionDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.collection.CollectionInteractor
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailDestination
import com.example.recipebook.presentation.viewModel.collectionDetailScreen.model.CollectionDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class
CollectionDetailViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val collectionId =
        checkNotNull(savedStateHandle[CollectionDetailDestination.COLLECTION_ID_ARG]).toString()
    private val _uiState = MutableStateFlow(CollectionDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCollectionDetail()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCollectionDetail() {
        collectionInteractor.getUserIdFlow()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(null)
                } else {
                    collectionInteractor.observeCollectionDetail(uid, collectionId)
                }
            }
            .onEach { collection ->
                if (collection == null) {
                    //TODO obBackEvent
                }

                if (collection != null) {


                    _uiState.update {
                        it.copy(
                            name = collection.name,
                            imageSource = collection.imageUrl,
                            description = collection.description,
                            collectionSize = collection.recipeIds.size,
                            recipeIds = collection.recipeIds,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun expandMenu(isExpand: Boolean) {
        _uiState.update {
            it.copy(isMenuExpanded = isExpand)
        }
    }
}