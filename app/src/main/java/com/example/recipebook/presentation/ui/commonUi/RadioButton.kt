package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun SelectableButtonBox(
    values: List<String>,
    selectedValue: String,
    onValueSelected: (String) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .selectableGroup()
    ) {
        values.forEachIndexed { index, gender ->

            Box(
                modifier = Modifier
                    .weight(1f)
                    .selectable(
                        selected = gender == selectedValue,
                        onClick = { onValueSelected(gender) },
                        role = Role.RadioButton
                    )
                    .border(
                        width = 1.dp,
                        color = if (gender == selectedValue) MaterialTheme.colorScheme.primary
                        else Color.Unspecified,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gender,
                    color = if (gender == selectedValue) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }

            if (index < values.size - 1) {
                Spacer(modifier = Modifier.width(28.dp))
            }
        }
    }
}