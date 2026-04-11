package com.example.recipebook.presentation.viewModel.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.model.AppResult
import com.example.recipebook.domain.model.error.SearchDataError
import com.example.recipebook.domain.useCase.recipe.searchRecipe.SearchRecipeUseCase
import com.example.recipebook.presentation.viewModel.model.RecipeUiState
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState
import com.example.recipebook.presentation.viewModel.searchScreen.model.ContentState
import com.example.recipebook.presentation.viewModel.searchScreen.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRecipeUseCase: SearchRecipeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()


    init {
        _uiState
            .map { it.searchText }
            .debounce(500L)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    _uiState.update { it.copy(contentState = ContentState.Empty) }
                } else {
                    searchRecipe(query)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChanged(newValue: String) {
        _uiState.update {
            it.copy(
                searchText = newValue
            )
        }
    }

    private fun searchRecipe(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    contentState = ContentState.Loading
                )
            }
            when (val result = searchRecipeUseCase.execute(query)) {
                is AppResult.Success -> {
                    _uiState.update {
                        it.copy(
                            contentState = if (result.data.isEmpty()) {
                                ContentState.NothingsFound
                            } else {
                                ContentState.SearchContent(result.data.map { recipe ->
                                    RecipeUiState(
                                        id = recipe.id,
                                        imageSource = recipe.imageUrl,
                                        category = recipe.category,
                                        name = recipe.recipeName,
                                        timeEstimationUiState = TimeEstimationUiState(
                                            hour = recipe.recipeTimeEstimation.hour,
                                            minute = recipe.recipeTimeEstimation.minute
                                        ),
                                        uploadedTime = recipe.createdAt
                                    )
                                })
                            }
                        )
                    }
                }

                is AppResult.Error ->
                    _uiState.update {
                        it.copy(
                            contentState = when (result.error) {
                                SearchDataError.NoInternet -> {
                                    ContentState.NoInternet
                                }

                                SearchDataError.Timeout -> {
                                    ContentState.NothingsFound
                                }

                                else -> {
                                    ContentState.UnknownError
                                }
                            }
                        )
                    }
            }
        }
    }
}