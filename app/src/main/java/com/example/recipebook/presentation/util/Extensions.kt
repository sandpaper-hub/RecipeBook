package com.example.recipebook.presentation.util

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
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
) = this.then(
    Modifier
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

@Composable
fun Long.toUpdatedAgoText(): String {
    val now = System.currentTimeMillis()
    val differenceTime = now - this

    val minutes = (differenceTime / 60_000).toInt()
    val hours = (differenceTime / 3_600_000).toInt()
    val days = (differenceTime / 86_400_000).toInt()
    val months = days / 30
    val years = months / 12

    return when {
        minutes < 1 ->
            stringResource(R.string.uploaded_now)

        minutes < 60 -> pluralStringResource(
            R.plurals.uploaded_minutes_ago,
            minutes, minutes
        )

        hours < 24 -> pluralStringResource(
            R.plurals.uploaded_hours_ago,
            hours, hours
        )

        days < 30 -> pluralStringResource(
            R.plurals.uploaded_days_ago,
            days, days
        )

        months < 12 -> pluralStringResource(
            R.plurals.uploaded_months_ago,
            months, months
        )

        else -> pluralStringResource(
            R.plurals.uploaded_years_ago,
            years, years
        )
    }
}

fun String.parseIngredient(): Pair<String, String> {
    val regex = Regex("""\d+([.,]\d+)?""")
    val match = regex.find(this) ?: return this to ""

    val index = match.range.first

    val name = this.substring(0, index).trim()
    val amount = this.substring(index).trim()

    return name to amount
}