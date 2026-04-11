package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePicker(
    isShow: Boolean,
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {


    if (isShow) {
        val state = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute
        )

        CompositionLocalProvider(LocalTonalElevationEnabled provides false) {
            TimePickerDialog(
                title = {
                    Text(
                        text = stringResource(R.string.time_estimation),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                },
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = {
                        onConfirm(state.hour, state.minute)
                        onDismiss()
                    }) {
                        Text(stringResource(R.string.ok_botton))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onDismiss() }) {
                        Text(stringResource(R.string.cancel_text))
                    }
                },
                modeToggleButton = {}
            ) {
                TimeInput(
                    state = state,
                    colors = TimePickerDefaults.colors(
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
            }
        }
    }
}