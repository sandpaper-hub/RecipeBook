package com.example.recipebook.util


import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Int.convertToFollowersFormat(): String {
    return when {
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000f)
        this >= 1_000 -> String.format("%.1fK", this / 1_000f)
        else -> this.toString()
    }.replace(".0", "")
}

fun String.convertToNickName(): String =
    trim().substringBefore("@")

fun String.fromLocaleCode(): String = when (this) {
    "ru" -> "Русский"
    "en" -> "English"
    else -> this
}

fun Long.toFormatedDate(
    locale: Locale = Locale.getDefault()
): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", locale)
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}

fun Modifier.dashBorder(
    color: Color,
    strokeWidth: Dp,
    dashWidth: Dp,
    gapWidth: Dp,
    shape: Shape
) = this.then(Modifier
    .padding(1.dp)
    .drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(
                    dashWidth.toPx(),
                    gapWidth.toPx()
                )
            )
        )

        val outline = shape.createOutline(
            size = size,
            layoutDirection = layoutDirection,
            density = this
        )

        when (outline) {
            is Outline.Rectangle -> {
                drawRect(
                    color = color,
                    style = stroke
                )
            }

            is Outline.Rounded -> {
                drawRoundRect(
                    color = color,
                    cornerRadius = outline.roundRect.bottomLeftCornerRadius,
                    style = stroke
                )
            }

            is Outline.Generic -> {
                drawPath(
                    path = outline.path,
                    color = color,
                    style = stroke
                )
            }
        }
    })

fun Int.plural(
    one: String,
    few: String,
    many: String
): String {
    val lastTwoDigits = this % 100
    val lastDigit = this % 10

    return when {
        lastTwoDigits in 11..14 -> many
        lastDigit == 1 -> one
        lastDigit in 2..4 -> few
        else -> many
    }
}