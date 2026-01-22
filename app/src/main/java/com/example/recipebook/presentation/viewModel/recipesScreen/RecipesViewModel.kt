package com.example.recipebook.presentation.viewModel.recipesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
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
class RecipesViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState

    init {
        observeUserRecipes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserRecipes() {
        recipesInteractor.getUserIdFlow()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    recipesInteractor.observeUserRecipes(uid)
                }
            }
            .onStart {
                _uiState.update {
                    it.copy(
                        isRecipesLoading = true
                    )
                }
            }
            .onEach { recipes ->
                _uiState.update {
                    it.copy(
                        recipes = recipes,
                        isRecipesLoading = false
                    )
                }
            }
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isRecipesLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}