package com.example.recipebook.presentation.ui.commonUi.dropDownMenu

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuResourceItem
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.MenuStringItem

@Composable
@Suppress("FunctionName")
fun IndexedDropdownMenu(
    menuItems: List<String>,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (index: Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismissRequest,
        scrollState = scrollState,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 12.dp,
        modifier = Modifier
            .heightIn(max = 300.dp)
            .widthIn(max = 200.dp)
    ) {
        menuItems.forEachIndexed { index, menuItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        maxLines = 1,
                        text = menuItem,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = { onItemClick(index) }
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun <T> ResourcesDropDownMenu(
    expanded: Boolean,
    items: List<DropdownMenuResourceItem<T>>,
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
                    Text(text = stringResource(item.titleResource))
                },
                onClick = {
                    onItemClick(item.action)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun <T> StringsDropDownMenu(
    expanded: Boolean,
    items: List<MenuStringItem<T>>,
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
                    Text(text = item.title)
                },
                onClick = {
                    onItemClick(item.action)
                    onDismiss()
                }
            )
        }
    }
}