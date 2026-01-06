package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.DarkModeBodyColor
import com.example.recipebook.theme.GreenAccent
import com.example.recipebook.theme.TitleGray
import com.example.recipebook.theme.TitleGrayTransparent

@Composable
@Suppress
fun MixedClickableText(
    simpleText: String,
    clickableText: String,
    onTextClicked: () -> Unit,
    modifier: Modifier
) {
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
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.then(Modifier.pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                val layoutResult = textLayoutResult ?: return@detectTapGestures
                val position = layoutResult.getOffsetForPosition(offset)

                annotatedText.getStringAnnotations("CLICK", position, position).firstOrNull()
                    ?.let {
                        onTextClicked()
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
fun SingleActionTextBox(
    value: String,
    hint: String,
    isError: Boolean?,
    contentDescription: String,
    onClick: () -> Unit,
    painter: Painter?,
    modifier: Modifier
) {

    val borderColor by animateColorAsState(
        if (isError == true) {
            MaterialTheme.colorScheme.error
        } else {
            Color.Unspecified
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .border(width = 0.5.dp, color = borderColor, shape = RoundedCornerShape(14.dp))
            .height(48.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        if (painter != null) {
            Icon(
                painter,
                contentDescription = contentDescription,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            text = value.ifEmpty { hint },
            color = if (value.isEmpty()) TitleGray else MaterialTheme.colorScheme.inversePrimary,
            style = if (value.isEmpty()) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Suppress("FunctionName")
fun DoubleActionTextBox(
    value: String,
    hint: String,
    onBoxClick: () -> Unit,
    onIconClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .height(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onBoxClick
            )
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value.ifBlank {
                hint
            },
            color = if (value.isEmpty()) TitleGray else MaterialTheme.colorScheme.inversePrimary,
            style = if (value.isEmpty()) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.delete_icon),
            contentDescription = stringResource(R.string.delete_icon),
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onIconClick
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
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
                .height(0.5.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Text(
            text = stringResource(R.string.or_continue),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.inversePrimary
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
@Suppress("FunctionName")
fun HeadingTextLarge(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
@Suppress
fun HeadingTextMedium(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
@Suppress("FunctionName")
fun SubHeadingTextSmall(
    text: String,
    color: Color,
    modifier: Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(color = color)
    )
}

@Composable
@Suppress("FunctionName")
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
@Suppress("FunctionName")
fun ClickableText(
    clickableText: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val clickableText = buildAnnotatedString {
        pushStringAnnotation(tag = "CLICK", annotation = clickableText)
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    Text(
        text = clickableText,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier.then(Modifier.pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                val layoutResult = textLayoutResult ?: return@detectTapGestures
                val position = layoutResult.getOffsetForPosition(offset)

                clickableText.getStringAnnotations("CLICK", position, position).firstOrNull()
                    ?.let {
                        onClick()
                    }
            }
        }),
        onTextLayout = { textLayoutResult = it })
}

@Composable
@Suppress("FunctionName")
fun SubheadingBackgroundText(
    text: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = TitleGrayTransparent),
        contentAlignment = Alignment.CenterStart
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkModeBodyColor
            ),
            modifier = Modifier
                .padding(start = 24.dp)
        )
    }
}

@Composable
@Suppress("FunctionName")
fun SelectableText(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else Color.Unspecified
            )
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else TitleGray,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (selected) {
            Icon(
                painter = painterResource(R.drawable.check_icon),
                contentDescription = stringResource(R.string.check_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}
