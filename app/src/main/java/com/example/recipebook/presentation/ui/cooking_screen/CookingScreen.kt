package com.example.recipebook.presentation.ui.cooking_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.presentation.viewModel.cookingScreen.CookingViewModel

@Composable
@Suppress("FunctionName")
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    Box(contentAlignment = Alignment.Center) {
        Text(uiState.id)
    }
}