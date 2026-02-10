package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.model.recipe.getRecipe.IngredientMeasure
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.DropdownMenuAction
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.IngredientUiState
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
        val recipeId =
            checkNotNull(savedStateHandle[RecipeDetailRoutes.RecipeDetail.RECIPE_ID_ARG]).toString()
        uiState = uiState.copy(id = recipeId)
        getRecipeById(recipeId)
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
                ingredients = recipe.ingredients.map {
                    IngredientUiState(
                        id = it.id,
                        value = it.value,
                        amount = it.amount,
                        measure = when (it.measure) {
                            IngredientMeasure.TEASPOON -> R.string.measure_teaspoon
                            IngredientMeasure.TABLESPOON -> R.string.measure_tablespoon
                            IngredientMeasure.MILLILITER -> R.string.measure_ml
                            IngredientMeasure.LITER -> R.string.measure_l
                            IngredientMeasure.GRAM -> R.string.measure_g
                            IngredientMeasure.KILOGRAM -> R.string.measure_kg
                            IngredientMeasure.PCS -> R.string.measure_pcs
                            else -> R.string.unknown_measure
                        }
                    )
                },
                createdAt = recipe.createdAt
            )
        }
    }

    fun openDeleteDialog(isOpen: Boolean) {
        uiState = uiState.copy(isOpedDeleteDialog = isOpen)
    }

    fun deleteRecipe(recipeId: String, onBack: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                recipesInteractor.deleteRecipe(recipeId)
            }
                .onSuccess {
                    onBack()
                }
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