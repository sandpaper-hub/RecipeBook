package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.recipebook.R
import com.example.recipebook.domain.Constraints
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.util.normalizeNumber
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState

@Composable
@Suppress("FunctionName")
fun IngredientDialog(
    editingIngredient: IngredientUiState?,
    onDismiss: () -> Unit,
    onConfirm: (editingIngredient: IngredientUiState) -> Unit
) {
    if (editingIngredient == null) return

    var draft by remember(editingIngredient.id) { mutableStateOf(editingIngredient) }
    var isMeasureMenuOpen by remember { mutableStateOf(false) }
    val measureMenuItems = listOf(
        MeasureMenuItem.TEASPOON,
        MeasureMenuItem.TABLESPOON,
        MeasureMenuItem.MILLILITER,
        MeasureMenuItem.LITER,
        MeasureMenuItem.GRAM,
        MeasureMenuItem.KILOGRAM,
        MeasureMenuItem.PCS
    )

    Dialog(onDismissRequest = onDismiss) {
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

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LimitedTextField(
                        value = draft.value,
                        onValueChange = { draft = draft.copy(value = it) },
                        onClearText = { draft = draft.copy(value = "") },
                        textLengthLimit = Constraints.MAX_INGREDIENT_LENGTH,
                        hint = stringResource(R.string.ingredient_name),
                        isError = draft.value.length > Constraints.MAX_INGREDIENT_LENGTH,
                        modifier = Modifier.height(52.dp)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CustomTextField(
                            value = draft.amount,
                            onValueChange = { draft = draft.copy(amount = it.normalizeNumber()) },
                            hint = stringResource(R.string.ingredient_amount),
                            keyboardType = KeyboardType.Decimal,
                            modifier = Modifier
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.background)
                        )

                        Column {
                            SingleActionText(
                                value = if (draft.measure != MeasureMenuItem.NULL) {
                                    stringResource(draft.measure.stringResource)
                                } else "",
                                hint = stringResource(R.string.empty_hint),
                                isError = false,
                                contentDescription = stringResource(R.string.measure),
                                onClick = { isMeasureMenuOpen = true },
                                painter = null,
                                modifier = Modifier.width(80.dp)
                            )

                            AppDropdownMenu(
                                expanded = isMeasureMenuOpen,
                                items = measureMenuItems,
                                itemContent = { menuItem ->
                                    Text(stringResource(menuItem.stringResource))
                                },
                                onItemClick = { menuItem ->
                                    draft = draft.copy(measure = menuItem)
                                },
                                onDismiss = { isMeasureMenuOpen = false },
                            )
                        }
                    }
                }

            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = {
                    onConfirm(draft)
                }) {
                    Text("OK")
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