package com.example.recipebook.presentation.viewModel.cookingScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(CookingUiState())

    init {
        val recipeId = checkNotNull(savedStateHandle[RecipeDetailRoutes.Cooking.RECIPE_ID_ARG]).toString()
        uiState = uiState.copy(id = recipeId)
    }
}