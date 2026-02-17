package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
@Composable
@Suppress("FunctionName")
fun <T> AppDropdownMenu(
    expanded: Boolean,
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
    onItemClick: (T) -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { itemContent(item) },
                onClick = {
                    onItemClick(item)
                    onDismiss()
                }
            )
        }
    }
}
