package com.example.recipebook.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.recipebook.ui.theme.GreenAccent
import com.example.recipebook.ui.theme.TextFieldBackground
import com.example.recipebook.ui.theme.TitleGray

@Composable
@Suppress("FunctionName")
fun SquareRoundedButton(
    onClick: () -> Unit, text: String, containerColor: Color?, modifier: Modifier
) {
    Button(
        onClick = onClick, modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
        ), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ?: GreenAccent, contentColor = Color.White
        )
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 14.dp))
    }
}


@Composable
@Suppress
fun SubHeadingClickableText(simpleText: String, clickableText: String, modifier: Modifier) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val annotatedText = buildAnnotatedString {
        append(simpleText)
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
        style = MaterialTheme.typography.titleSmall,
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
        modifier = modifier
            .then(Modifier)
            .background(
                color = TextFieldBackground, shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint, color = TitleGray, style = MaterialTheme.typography.titleMedium
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleSmall,
            singleLine = true,
            )
    }
}
