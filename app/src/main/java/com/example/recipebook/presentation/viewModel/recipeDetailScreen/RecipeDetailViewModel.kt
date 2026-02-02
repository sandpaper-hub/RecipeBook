package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.navigation.mainHomeGraph.recipesGraph.RecipesRoutes
import com.example.recipebook.presentation.ui.model.DropdownMenuItem
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.DropdownMenuAction
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.RecipeDetailUiState
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
        initDropdownMenuItems()
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

    private fun initDropdownMenuItems() {
        val dropdownMenuItems = listOf(
            DropdownMenuItem(
                action = DropdownMenuAction.EDIT,
                titleResource = R.string.edit_text
            ),
            DropdownMenuItem(
                action = DropdownMenuAction.DELETE,
                titleResource = R.string.delete_text
            )
        )

        uiState = uiState.copy(
            dropdownMenuItems = dropdownMenuItems
        )
    }

    fun isOpenDropdownMenu(isOpen: Boolean) {
        uiState = uiState.copy(isOpenDropdownMenu = isOpen)
    }
}