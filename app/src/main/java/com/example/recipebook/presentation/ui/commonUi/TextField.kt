package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.TitleGray

@Composable
@Suppress("FunctionName")
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isError: Boolean,
    modifier: Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else if (isError) {
            MaterialTheme.colorScheme.error
        } else
            Color.Transparent
    )
    val borderWidth by animateDpAsState(
        if (isFocused) 0.5.dp else 1.dp
    )

    Box(
        modifier = modifier.then(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(14.dp)
                )
                .height(48.dp)
                .padding(16.dp)
        )
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint,
                color = TitleGray,
                style = MaterialTheme.typography.titleSmall
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    isFocused = state.isFocused
                },
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
@Suppress("FunctionName")
fun CustomNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isError: Boolean,
    modifier: Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else if (isError) {
            MaterialTheme.colorScheme.error
        } else
            Color.Transparent
    )
    val borderWidth by animateDpAsState(
        if (isFocused) 0.5.dp else 1.dp
    )

    Box(
        modifier = modifier.then(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(14.dp)
                )
                .height(48.dp)
                .padding(16.dp)
        )
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint,
                color = TitleGray,
                style = MaterialTheme.typography.titleSmall
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    isFocused = state.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
@Suppress("FunctionName")
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isError: Boolean,
    modifier: Modifier,
    visible: Boolean = false,
    changeVisibility: () -> Unit,
    enabled: Boolean = true
) {
    val interaction = remember { MutableInteractionSource() }
    val isFocused by interaction.collectIsFocusedAsState()
    val shape = RoundedCornerShape(14.dp)
    val borderColor by animateColorAsState(
        when {
            isError -> MaterialTheme.colorScheme.error
            isFocused -> MaterialTheme.colorScheme.primary
            else -> Color.Transparent
        }
    )
    val borderWidth by animateDpAsState(
        if (isFocused) 0.5.dp else 1.dp
    )

    Box(
        modifier = modifier.then(
            Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant, shape = shape)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = shape
                )
                .height(48.dp)
                .padding(16.dp)
        )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary),
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interaction,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.titleMedium,
                                color = TitleGray
                            )
                        }
                        innerTextField()
                    }
                    Box(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { changeVisibility() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (visible) {
                                stringResource(R.string.hide_password)
                            } else {
                                stringResource(
                                    R.string.show_password
                                )
                            },
                            tint = TitleGray
                        )
                    }
                }
            })
    }
}
