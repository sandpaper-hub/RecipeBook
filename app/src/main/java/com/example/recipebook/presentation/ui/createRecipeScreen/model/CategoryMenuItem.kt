package com.example.recipebook.presentation.ui.createRecipeScreen.model

import com.example.recipebook.R

enum class CategoryMenuItem(val stringResource: Int) {
    APPETIZER(stringResource = R.string.appetizer),
    SALAD(stringResource = R.string.salad),
    SOUP(stringResource = R.string.soup),
    MAIN(stringResource = R.string.main),
    GARNISH(stringResource = R.string.garnish),
    SAUCE(stringResource = R.string.sauce),
    DESERT(stringResource = R.string.desert),
    DRINK(stringResource = R.string.drink);

    companion object {
        fun from(value: String): CategoryMenuItem =
            CategoryMenuItem.valueOf(value)
    }
}