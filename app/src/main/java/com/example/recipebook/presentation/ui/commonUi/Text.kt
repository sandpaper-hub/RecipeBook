package com.example.recipebook.presentation.ui.commonUi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.GreenAccent
import com.example.recipebook.theme.TitleGray

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
        style = MaterialTheme.typography.titleMedium,
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
fun HeadingTextLarge(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(top = 24.dp),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
@Suppress
fun HeadingTextMedium(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
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
        style = MaterialTheme.typography.titleMedium,
        color = color
    )
}

@Composable
@Suppress("FunctionName")
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
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
            append(clickableText)
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
            .background(color = Color(0x0D757575)),
        contentAlignment = Alignment.CenterStart
    ) {

        Text(
            text = text,
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
            style = MaterialTheme.typography.displayLarge,
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
