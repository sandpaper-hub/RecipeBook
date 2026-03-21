package com.example.recipebook.presentation.viewModel.cookingScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.getRecipeSteps.GetRecipeStepsUseCase
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailDestination
import com.example.recipebook.presentation.viewModel.cookingScreen.model.CookingEvent
import com.example.recipebook.presentation.viewModel.cookingScreen.model.CookingUiState
import com.example.recipebook.presentation.viewModel.cookingScreen.model.StepUiState
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
    private val getRecipeStepsUseCase: GetRecipeStepsUseCase,
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
            val steps = getRecipeStepsUseCase.execute(recipeId)
            _uiState.update { cookingUiState ->
                cookingUiState.copy(
                    recipeSteps = steps.mapIndexed { index, step ->
                        StepUiState(
                            index = index,
                            title = step.title,
                            order = step.order,
                            description = step.description,
                            imageUrl = step.imageSource
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

    fun onBack() {
        viewModelScope.launch {
            _events.emit(
                CookingEvent.GoBack
            )
        }
    }
}