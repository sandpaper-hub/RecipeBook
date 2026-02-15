package com.example.recipebook.presentation.ui.collectionDetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.presentation.viewModel.CollectionDetailViewModel

@Composable
@Suppress("FunctionName")
fun CollectionDetailScreen(
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("CollectionDetailScreen")
    }
}