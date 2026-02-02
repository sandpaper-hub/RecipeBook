package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipebook.presentation.ui.model.DropdownMenuItem

@Composable
@Suppress("FunctionName")
fun CustomDropDownMenu(
    menuItems: List<String>,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        val scrollState = rememberScrollState()

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismissRequest,
            scrollState = scrollState,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.heightIn(max = 300.dp)
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(
                    text = { Text(text = menuItem) },
                    onClick = { onItemClick(menuItem) },
                )
            }
        }
    }
}

@Composable
@Suppress("FunctionName")
fun <T> CustomDropDownMenuNew(
    expanded: Boolean,
    items: List<DropdownMenuItem<T>>,
    onDismiss: () -> Unit,
    onItemClick: (T) -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = when {
                            item.titleResource != null -> {
                                stringResource(item.titleResource)
                            }

                            item.title != null -> {
                                item.title
                            }

                            else -> error("No title resource")
                        }
                    )
                },
                onClick = {
                    onItemClick(item.action)
                    onDismiss()
                }
            )
        }
    }
}