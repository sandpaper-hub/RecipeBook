package com.example.recipebook.presentation.ui.recipesScreen.tabs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
@Suppress("FunctionName")
fun DraftTab(
    listState: LazyListState
) {
    LazyColumn(state = listState) {
        items(50) { item ->
            Text("$item draft")
        }
    }
}