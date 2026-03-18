package com.example.recipebook.presentation.viewModel.collectionsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCase
import com.example.recipebook.domain.useCase.userProfile.getUserIdFlow.GetUserIdFlowUseCase
import com.example.recipebook.presentation.viewModel.collectionsScreen.model.CollectionsUiEvents
import com.example.recipebook.presentation.viewModel.collectionsScreen.model.CollectionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val observeUserCollectionUseCase: ObserveUserCollectionUseCase
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<CollectionsUiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()
    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState

    init {
        observeUserCollections()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserCollections() {
        getUserIdFlowUseCase.execute()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    observeUserCollectionUseCase.execute(uid)
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

    fun onCollectionDetail(collectionId: String) {
        viewModelScope.launch {
            _uiEvents.emit(
                CollectionsUiEvents.CollectionDetail(collectionId)
            )
        }
    }

}