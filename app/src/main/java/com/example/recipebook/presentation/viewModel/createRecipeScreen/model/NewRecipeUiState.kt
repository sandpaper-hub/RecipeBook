package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import android.net.Uri
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem

data class NewRecipeUiState(
    val recipeImageUri: Uri? = null,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val categoryMenuItems: List<DropdownMenuItem<CategoryMenuAction>> = listOf(
        DropdownMenuItem(
            action = CategoryMenuAction.APPETIZER,
            titleResource = R.string.appetizer
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.SALAD,
            titleResource = R.string.salad
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.SOUP,
            titleResource = R.string.soup
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.MAIN,
            titleResource = R.string.main
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.GARNISH,
            titleResource = R.string.garnish
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.SAUCE,
            titleResource = R.string.sauce
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.DESERT,
            titleResource = R.string.desert
        ),
        DropdownMenuItem(
            action = CategoryMenuAction.DRINK,
            titleResource = R.string.drink
        )
    ),
    val measureMenuItems: List<DropdownMenuItem<MeasureMenuAction>> = listOf(
        DropdownMenuItem(
            action = MeasureMenuAction.TEASPOON,
            titleResource = R.string.measure_teaspoon
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.TABLESPOON,
            titleResource = R.string.measure_tablespoon
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.GRAM,
            titleResource = R.string.measure_g,
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.KILOGRAM,
            titleResource = R.string.measure_kg
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.MILLILITER,
            titleResource = R.string.measure_ml
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.LITER,
            titleResource = R.string.measure_l
        ),
        DropdownMenuItem(
            action = MeasureMenuAction.PCS,
            titleResource = R.string.measure_pcs
        )
    ),
    val ingredients: List<IngredientUiState> = emptyList(),
    val recipeSteps: List<RecipeStepUiState> = emptyList(),
    val recipeCategory: CategoryMenuAction = CategoryMenuAction.UNKNOWN,
    val isCategoryMenuExpand: Boolean = false,
    val errorMessage: String? = null
)