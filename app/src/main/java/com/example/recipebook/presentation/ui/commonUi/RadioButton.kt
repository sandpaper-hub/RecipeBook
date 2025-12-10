package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.TitleGray

@Composable
@Suppress("FunctionName")
fun SelectableTextBox(
    values: List<String>,
    modifier: Modifier
) {
    var selectedOption by remember { mutableStateOf(values[0]) }

    Column(
        modifier = modifier
            .selectableGroup()
    ) {
        values.forEach { language ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = (language == selectedOption),
                        onClick = { selectedOption = language },
                        role = Role.RadioButton
                    )
                    .background(
                        color = if (language == selectedOption) {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        } else Color.Unspecified
                    )
            ) {
                Text(
                    text = language,
                    color = if (selectedOption == language) MaterialTheme.colorScheme.onPrimary else TitleGray,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(start = 24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                if (language == selectedOption) {
                    Icon(
                        painterResource(R.drawable.check_icon),
                        contentDescription = stringResource(R.string.check_icon),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 24.dp)
                    )
                }
            }
        }
    }
}


@Composable
@Suppress("FunctionName")
fun SelectableButtonBox(
    values: List<String>,
    modifier: Modifier
) {
    var selectedOption by remember { mutableStateOf(values[0]) }

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
                        selected = (selectedOption == gender),
                        onClick = { selectedOption = gender },
                        role = Role.RadioButton
                    )
                    .border(
                        width = 1.dp,
                        color = if (gender == selectedOption) MaterialTheme.colorScheme.primary
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
                    color = if (gender == selectedOption) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }

            if (index < values.size - 1) {
                Spacer(modifier = Modifier.width(28.dp))
            }
        }
    }
}