package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem

@Composable
@Suppress("FunctionName")
fun IngredientDialog(
    onDialogDismiss: () -> Unit,
    onConfirm: (value: String, amount: String, measure: String) -> Unit
) {
    val measureMenuItems = listOf(
        MeasureMenuItem.TEASPOON,
        MeasureMenuItem.TABLESPOON,
        MeasureMenuItem.MILLILITER,
        MeasureMenuItem.LITER,
        MeasureMenuItem.GRAM,
        MeasureMenuItem.KILOGRAM,
        MeasureMenuItem.PCS
    )

    var ingredientValue by rememberSaveable { mutableStateOf("") }
    var isValueError by rememberSaveable { mutableStateOf(false) }
    var ingredientAmount by rememberSaveable { mutableStateOf("") }
    var isAmountError by rememberSaveable { mutableStateOf(false) }
    var ingredientMeasure by rememberSaveable { mutableStateOf(MeasureMenuItem.NULL) }
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
                            value = if (ingredientMeasure.stringResource != 0) {
                                stringResource(ingredientMeasure.stringResource)
                            } else "",
                            hint = stringResource(R.string.empty_hint),
                            isError = isMeasureError,
                            contentDescription = stringResource(R.string.measure),
                            onClick = { isMenuExpanded = true },
                            painter = null,
                            modifier = Modifier.width(60.dp)
                        )

                        AppDropdownMenu(
                            expanded = isMenuExpanded,
                            items = measureMenuItems,
                            itemContent = { menuItem ->
                                Text(stringResource(menuItem.stringResource))
                            },
                            onItemClick = { menuItem ->
                                ingredientMeasure = menuItem
                                isMeasureError = false
                            },
                            onDismiss = { isMenuExpanded = false },
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
                            ingredientMeasure == MeasureMenuItem.NULL -> isMeasureError = true
                        }

                        if (!isValueError && !isAmountError && !isMeasureError) {
                            onConfirm(ingredientValue, ingredientAmount, ingredientMeasure.name)
                        }
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
@Suppress("FunctionName")
fun ConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    recipeName: String
) {

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            ),
            modifier = Modifier.width(320.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                HeadingTextMedium(
                    text = stringResource(R.string.delete_recipe_title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                SubHeadingTextSmall(
                    text = "${stringResource(R.string.delete_description_title)} \"$recipeName\"?\n" +
                            stringResource(R.string.delete_warning_title),
                    modifier = Modifier
                        .padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SquareRoundedButton(
                        onClick = onDismiss,
                        text = stringResource(R.string.cancel_text),
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier.weight(1f)
                    )

                    SquareRoundedButton(
                        onClick = onConfirm,
                        text = stringResource(R.string.delete_text),
                        containerColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}