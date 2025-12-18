package com.example.recipebook.util


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