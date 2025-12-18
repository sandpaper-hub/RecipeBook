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
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun CustomDropDownMenu(
    countryList: List<String>,
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
            countryList.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = { onItemClick(country) },
                )
            }
        }
    }
}