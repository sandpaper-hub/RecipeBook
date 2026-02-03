package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.CustomDropDownMenuNew
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.MeasureMenuAction


@Composable
@Suppress("FunctionName")
fun IngredientDialog(
    items: List<DropdownMenuItem<MeasureMenuAction>>,
    onDialogDismiss: () -> Unit,
    onConfirm: (value:String, amount:String, measure:MeasureMenuAction) -> Unit
) {

    var ingredientValue by rememberSaveable { mutableStateOf("") }
    var isValueError by rememberSaveable { mutableStateOf(false) }
    var ingredientAmount by rememberSaveable { mutableStateOf("") }
    var isAmountError by rememberSaveable { mutableStateOf(false) }
    var ingredientMeasure by rememberSaveable { mutableStateOf<MeasureMenuAction?>(null) }
    var isMeasureError by rememberSaveable { mutableStateOf(false) }
    var isMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = onDialogDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                TitleText(
                    text = stringResource(R.string.ingredient_measure),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row {
                    CustomTextField(
                        value = ingredientValue,
                        onValueChange = {
                            ingredientValue = it
                            isValueError = false
                        },
                        hint = stringResource(R.string.enter_ingredient),
                        isError = isValueError,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CustomNumberTextField(
                        value = ingredientAmount,
                        onValueChange = {
                            ingredientAmount = it
                            isAmountError = false
                        },
                        hint = "100",
                        isError = isAmountError,
                        modifier = Modifier
                            .width(60.dp)
                            .background(MaterialTheme.colorScheme.background)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        SingleActionTextBox(
                            value = ingredientMeasure?.let {
                                stringResource(
                                    when (it) {
                                        MeasureMenuAction.TEASPOON -> R.string.measure_teaspoon
                                        MeasureMenuAction.TABLESPOON -> R.string.measure_tablespoon
                                        MeasureMenuAction.GRAM -> R.string.measure_g
                                        MeasureMenuAction.KILOGRAM -> R.string.measure_kg
                                        MeasureMenuAction.MILLILITER -> R.string.measure_ml
                                        MeasureMenuAction.LITER -> R.string.measure_l
                                        MeasureMenuAction.PCS -> R.string.measure_pcs
                                    }
                                )
                            } ?: "",
                            hint = stringResource(R.string.empty_hint),
                            isError = isMeasureError,
                            contentDescription = stringResource(R.string.measure),
                            onClick = { isMenuExpanded = true },
                            painter = null,
                            modifier = Modifier.width(60.dp)
                        )

                        CustomDropDownMenuNew(
                            expanded = isMenuExpanded,
                            items = items,
                            onDismiss = { isMenuExpanded = false },
                            onItemClick = { selectedMeasure ->
                                ingredientMeasure = selectedMeasure
                                isMeasureError = false
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = onDialogDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = {
                        when {
                            ingredientValue.isBlank() -> isValueError = true
                            ingredientAmount.isBlank() -> isAmountError = true
                            ingredientMeasure == null -> isMeasureError = true
                        }

                        if (!isValueError && !isAmountError && !isMeasureError) {
                            onConfirm(ingredientValue, ingredientAmount, ingredientMeasure!!)
                        }
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}