package com.example.recipebook.presentation.ui.createRecipeScreen.model

import com.example.recipebook.R

enum class MeasureMenuItem(val stringResource: Int) {
    TEASPOON(stringResource = R.string.measure_teaspoon),
    TABLESPOON(stringResource = R.string.measure_tablespoon),
    MILLILITER(stringResource = R.string.measure_ml),
    LITER(stringResource = R.string.measure_l),
    GRAM(stringResource = R.string.measure_g),
    KILOGRAM(stringResource = R.string.measure_kg),
    PCS(stringResource = R.string.measure_pcs),
    NULL(stringResource = 0);

    companion object{
        fun from(value: String): MeasureMenuItem =
            MeasureMenuItem.valueOf(value)
    }
}