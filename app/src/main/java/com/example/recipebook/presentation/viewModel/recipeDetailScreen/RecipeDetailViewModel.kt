package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.navigation.mainHomeGraph.recipesGraph.RecipesRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(RecipeDetailUiState())

    init {
        val recipeId = checkNotNull(savedStateHandle[RecipesRoutes.RecipeDetail.RECIPE_ID_ARG])
        getRecipeById(recipeId.toString())
    }

    private fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            val recipe = recipesInteractor.getRecipeById(recipeId)
            uiState = uiState.copy(
                imageUrl = recipe.imageUrl,
                name = recipe.recipeName,
                description = recipe.recipeDescription,
                category = recipe.category,
                timeEstimation = recipe.recipeTimeEstimation,
                ingredients = recipe.ingredients.map { it.value },
                steps = recipe.steps,
                createdAt = recipe.createdAt
            )
        }
    }
}