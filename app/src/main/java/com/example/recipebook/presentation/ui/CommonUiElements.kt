package com.example.recipebook.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.GreenAccent
import com.example.recipebook.theme.TitleGray

@Composable
@Suppress("FunctionName")
fun SquareRoundedButton(
    onClick: () -> Unit, text: String, containerColor: Color?, modifier: Modifier
) {
    Button(
        onClick = onClick, modifier = modifier.then(
            Modifier
                .height(49.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ?: GreenAccent, contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}


@Composable
@Suppress
fun MixedClickableText(simpleText: String, clickableText: String, modifier: Modifier) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val annotatedText = buildAnnotatedString {

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inversePrimary)) {
            append(simpleText)
        }
        pushStringAnnotation(tag = "CLICK", annotation = clickableText)
        withStyle(
            style = SpanStyle(
                color = GreenAccent, textDecoration = TextDecoration.None
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    Text(
        annotatedText,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.then(Modifier.pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                val layoutResult = textLayoutResult ?: return@detectTapGestures
                val position = layoutResult.getOffsetForPosition(offset)

                annotatedText.getStringAnnotations("CLICK", position, position).firstOrNull()
                    ?.let { annotation ->
                        //TODO
                        Log.d("CLICKABLE TEXT", "CLICKED $annotation")
                    }
            }
        }),
        onTextLayout = { textLayoutResult = it },
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@Suppress("FunctionName")
fun CustomTextField(
    value: String, onValueChange: (String) -> Unit, hint: String, modifier: Modifier
) {
    Box(
        modifier = modifier.then(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(14.dp)
                )
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
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inversePrimary),
            singleLine = true
        )
    }
}

@Composable
@Suppress("FunctionName")
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier,
    visible: Boolean = false,
    changeVisibility: () -> Unit,
    enabled: Boolean = true,
    onDone: (() -> Unit)? = null
) {
    val interaction = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier.then(
            Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant, shape = shape)
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
                imeAction = if (onDone != null) ImeAction.Done else ImeAction.Default
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone?.invoke() }),
            modifier = Modifier.fillMaxWidth(),
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
                            .size(20.dp)
                            .clickable { changeVisibility() },
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

@Composable
@Suppress("FunctionName")
fun TextDivider(modifier: Modifier) {
    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth()
        ), verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp), color = MaterialTheme.colorScheme.inversePrimary
        )

        Text(
            text = stringResource(R.string.or_continue),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp), color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
@Suppress("FunctionName")
fun OutlinedIconButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onClick, modifier = modifier
            .height(52.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
@Suppress("FunctionName")
fun HeadingText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(top = 24.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
@Suppress("FunctionName")
fun SubHeadingText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

@Composable
@Suppress("FunctionName")
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
@Suppress("FunctionName")
fun ClickableText(clickableText: String, modifier: Modifier) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val clickableText = buildAnnotatedString {
        pushStringAnnotation(tag = "CLICK", annotation = clickableText)
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            append("Forgot password?")
        }
        pop()
    }

    Text(
        text = clickableText,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier.then(Modifier.pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                val layoutResult = textLayoutResult ?: return@detectTapGestures
                val position = layoutResult.getOffsetForPosition(offset)

                clickableText.getStringAnnotations("CLICK", position, position).firstOrNull()
                    ?.let { annotation ->
                        //TODO
                        Log.d("CLICKABLE TEXT", "CLICKED $annotation")
                    }
            }
        }),
        onTextLayout = { textLayoutResult = it })
}