package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import android.net.Uri
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuResourceItem

data class NewRecipeUiState(
    val recipeImageUri: Uri? = null,
    val recipeName: String = "",
    val recipeDescription: String = "",
    val timeEstimation: String = "",
    val editingIngredientId: String? = null,
    val categoryMenuItems: List<DropdownMenuResourceItem<CategoryMenuAction>> = listOf(
        DropdownMenuResourceItem(
            action = CategoryMenuAction.APPETIZER,
            titleResource = R.string.appetizer
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.SALAD,
            titleResource = R.string.salad
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.SOUP,
            titleResource = R.string.soup
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.MAIN,
            titleResource = R.string.main
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.GARNISH,
            titleResource = R.string.garnish
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.SAUCE,
            titleResource = R.string.sauce
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.DESERT,
            titleResource = R.string.desert
        ),
        DropdownMenuResourceItem(
            action = CategoryMenuAction.DRINK,
            titleResource = R.string.drink
        )
    ),
    val measureMenuItems: List<DropdownMenuResourceItem<MeasureMenuAction>> = listOf(
        DropdownMenuResourceItem(
            action = MeasureMenuAction.TEASPOON,
            titleResource = R.string.measure_teaspoon
        ),
        DropdownMenuResourceItem(
            action = MeasureMenuAction.TABLESPOON,
            titleResource = R.string.measure_tablespoon
        ),
        DropdownMenuResourceItem(
            action = MeasureMenuAction.GRAM,
            titleResource = R.string.measure_g,
        ),
        DropdownMenuResourceItem(
            action = MeasureMenuAction.KILOGRAM,
            titleResource = R.string.measure_kg
        ),
        DropdownMenuResourceItem(
            action = MeasureMenuAction.MILLILITER,
            titleResource = R.string.measure_ml
        ),
        DropdownMenuResourceItem(
            action = MeasureMenuAction.LITER,
            titleResource = R.string.measure_l
        ),
        DropdownMenuResourceItem(
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