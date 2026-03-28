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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState

@Composable
@Suppress("FunctionName")
fun IngredientDialog(
    editingIngredient: IngredientUiState?,
    isMeasureMenuOpen: Boolean,
    showMeasureMenu: (Boolean) -> Unit,
    onEditingIngredientChange: (ingredient: IngredientUiState) -> Unit,
    onDialogDismiss: () -> Unit,
    onConfirm: (editingIngredient: IngredientUiState) -> Unit
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

    if (editingIngredient != null) {


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
                    BodyMediumText(
                        text = stringResource(R.string.ingredient_measure),
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Row {
                        CustomTextField(
                            value = editingIngredient.value,
                            onValueChange = {
                                onEditingIngredientChange(
                                    editingIngredient.copy(
                                        value = it
                                    )
                                )
                            },
                            hint = stringResource(R.string.enter_ingredient),
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        CustomTextField(
                            value = editingIngredient.amount,
                            onValueChange = {
                                onEditingIngredientChange(
                                    editingIngredient.copy(
                                        amount = it
                                    )
                                )
                            },
                            hint = "100",
                            keyboardType = KeyboardType.Decimal,
                            modifier = Modifier
                                .width(60.dp)
                                .background(MaterialTheme.colorScheme.background)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            SingleActionText(
                                value = if (editingIngredient.measure != MeasureMenuItem.NULL) {
                                    stringResource(editingIngredient.measure.stringResource)
                                } else "",
                                hint = stringResource(R.string.empty_hint),
                                isError = false,
                                contentDescription = stringResource(R.string.measure),
                                onClick = { showMeasureMenu(true) },
                                painter = null,
                                modifier = Modifier.width(60.dp)
                            )

                            AppDropdownMenu(
                                expanded = isMeasureMenuOpen,
                                items = measureMenuItems,
                                itemContent = { menuItem ->
                                    Text(stringResource(menuItem.stringResource))
                                },
                                onItemClick = { menuItem ->
                                    onEditingIngredientChange(
                                        editingIngredient.copy(
                                            measure = menuItem
                                        )
                                    )
                                },
                                onDismiss = { showMeasureMenu(false) },
                            )
                        }
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
                        onConfirm(editingIngredient)
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
fun DeleteDialog(
    headingText: String,
    warningText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    itemName: String
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
                HeadingMediumText(
                    text = headingText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                BodyMediumText(
                    text = "${stringResource(R.string.delete_description_title)} \"$itemName\"?\n" +
                            warningText,
                    modifier = Modifier
                        .padding(top = 8.dp)
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