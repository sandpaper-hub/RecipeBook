package com.example.recipebook.presentation.util

import android.os.SystemClock
import java.util.concurrent.atomic.AtomicLong

fun debounce(
    delayMillis: Long = 500L,
    action: () -> Unit
): () -> Unit {
    val lastClick = AtomicLong(0L)
    return {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClick.get() >= delayMillis) {
            lastClick.set(now)
            action()
        }
    }
}