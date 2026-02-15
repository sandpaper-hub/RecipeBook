package com.example.recipebook.presentation.viewModel.cookingScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _events = MutableSharedFlow<CookingEvent>()
    val events = _events.asSharedFlow()
    private val _uiState = MutableStateFlow(CookingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val recipeId =
            checkNotNull(savedStateHandle[RecipeDetailDestination.RECIPE_ID_ARG]).toString()
        _uiState.update { it.copy(recipeId = recipeId) }
        getRecipeStepsById(recipeId)
    }

    fun getRecipeStepsById(recipeId: String) {
        viewModelScope.launch {
            val steps = recipesInteractor.getRecipeSteps(recipeId)
            _uiState.update { cookingUiState ->
                cookingUiState.copy(
                    recipeSteps = steps.map {
                        StepUiState(
                            title = it.title,
                            order = it.order,
                            description = it.description,
                            imageUrl = it.imageSource
                        )
                    }
                )
            }
        }
    }

    fun expandPagesMenu(isExpand: Boolean) {
        _uiState.update {
            it.copy(isPagesMenuExpanded = isExpand)
        }
    }

    fun goToPage(index: Int) {
        viewModelScope.launch {
            _events.emit(CookingEvent.GoToPage(index))
        }
    }
}