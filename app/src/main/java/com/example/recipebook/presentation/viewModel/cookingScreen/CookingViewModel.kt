package com.example.recipebook.presentation.viewModel.cookingScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(CookingUiState())

    init {
        val recipeId = checkNotNull(savedStateHandle[RecipeDetailRoutes.Cooking.RECIPE_ID_ARG]).toString()
        uiState = uiState.copy(recipeId = recipeId)
        getRecipeStepsById(recipeId)
    }

    fun getRecipeStepsById(recipeId: String) {
        viewModelScope.launch {
            val steps = recipesInteractor.getRecipeSteps(recipeId)
            uiState = uiState.copy(
                recipeSteps = steps.map {
                    StepUiState(
                        title = it.title,
                        description = it.description,
                        imageUrl = it.imageSource
                    )
                }
            )
        }
    }
}