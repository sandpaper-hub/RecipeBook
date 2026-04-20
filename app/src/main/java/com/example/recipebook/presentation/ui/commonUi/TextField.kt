package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.TitleGray

@Composable
private fun Modifier.setTextFieldModifier(
    isFocused: Boolean,
    isError: Boolean
): Modifier {
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

    return this
        .background(
            color = MaterialTheme.colorScheme.inverseSurface,
            shape = RoundedCornerShape(14.dp)
        )
        .border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(14.dp)
        )
        .height(52.dp)
        .padding(16.dp)
}

@Composable
@Suppress("FunctionName")
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    modifier: Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.then(
            Modifier.setTextFieldModifier(
                isError = isError,
                isFocused = isFocused
            )
        ),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = TitleGray
                )
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
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.inversePrimary),
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
    passwordVisibility: Boolean = false,
    changeVisibility: () -> Unit,
    enabled: Boolean = true
) {
    val interaction = remember { MutableInteractionSource() }
    val isFocused by interaction.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.then(
            Modifier.setTextFieldModifier(
                isFocused = isFocused,
                isError = isError
            )
        )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.inversePrimary
            ),
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interaction,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.labelMedium,
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
                            imageVector = if (passwordVisibility) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (passwordVisibility) {
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

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    hint: String,
    isError: Boolean = false,
    modifier: Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.then(
            Modifier.setTextFieldModifier(isFocused = isFocused, isError = isError)
        )
    ) {
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
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = stringResource(R.string.search_icon),
                            tint = MaterialTheme.colorScheme.inversePrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.labelMedium,
                                color = TitleGray
                            )
                        }
                        innerTextField()
                    }

                    if (value.isNotBlank() && isFocused) {
                        Box(
                            modifier = Modifier
                                .clickable(
                                    onClick = onClearText,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                )
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.cancel_icon),
                                contentDescription = stringResource(R.string.cancel_icon),
                                tint = MaterialTheme.colorScheme.inversePrimary
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun LimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    textLengthLimit: Int,
    hint: String,
    isError: Boolean = false,
    modifier: Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.then(
            Modifier.setTextFieldModifier(isFocused = isFocused, isError = isError)
        )
    ) {
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
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isBlank()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.labelMedium,
                                color = TitleGray
                            )
                        }
                        innerTextField()
                    }

                    if (isFocused) {
                        SecondaryText(
                            text = "${value.length}/$textLengthLimit",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = if (value.length > textLengthLimit) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onPrimary
                                }
                            )
                        )
                    }

                    if (value.isNotBlank() && isFocused) {
                        Box(
                            modifier = Modifier
                                .clickable(
                                    onClick = onClearText,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                )
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.cancel_icon),
                                contentDescription = stringResource(R.string.cancel_icon),
                                tint = MaterialTheme.colorScheme.inversePrimary
                            )
                        }
                    }
                }
            }
        )
    }
}