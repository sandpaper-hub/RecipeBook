package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.recipebook.presentation.controller.LocalSnackBarController
import com.example.recipebook.presentation.controller.SnackBarController

@Composable
@Suppress("FunctionName")
fun RootScaffold(
    bottomBar: @Composable (() -> Unit)? = null,
    applySystemInsets: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarController = remember(snackBarHostState, scope){
        SnackBarController(snackBarHostState, scope)
    }

    CompositionLocalProvider(LocalSnackBarController provides snackBarController) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = {bottomBar?.invoke()},
            contentWindowInsets = if (applySystemInsets) {
                androidx.compose.foundation.layout.WindowInsets.systemBars
            } else {
                androidx.compose.foundation.layout.WindowInsets(0)
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}