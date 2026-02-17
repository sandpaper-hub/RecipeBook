package com.example.recipebook.presentation.viewModel.collectionsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.collection.CollectionInteractor
import com.example.recipebook.presentation.viewModel.collectionsScreen.model.CollectionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val collectionsInteractor: CollectionInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState

    init {
        observeUserCollections()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserCollections() {
        collectionsInteractor.getUserIdFlow()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    collectionsInteractor.observeUserCollections(uid)
                }
            }
            .onStart {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            .onEach { collections ->
                _uiState.update {
                    it.copy(
                        collections = collections,
                        isLoading = false
                    )
                }
            }
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}