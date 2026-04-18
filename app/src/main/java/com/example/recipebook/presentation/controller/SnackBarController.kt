package com.example.recipebook.presentation.controller

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarController internal constructor(
    private val hostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    private var currentJob: Job? = null
    fun showMessage(message: String?) {
        if (message != null) {
            currentJob?.cancel()
            currentJob = scope.launch {
                hostState.currentSnackbarData?.dismiss()
                hostState.showSnackbar(message)
            }
        }
    }
}

val LocalSnackBarController = staticCompositionLocalOf<SnackBarController> {
    error("SnackBarController isn't provided")
}