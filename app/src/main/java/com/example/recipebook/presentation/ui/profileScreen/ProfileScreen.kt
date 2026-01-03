package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel
import com.example.recipebook.util.debounce

@Composable
@Suppress("FunctionName")
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onSettings: () -> Unit,
    onEditProfile: () -> Unit
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        item {
            ProfileHeader(
                imageUrl = uiState.profileImageUrl,
                profileName = uiState.fullName,
                profileNickName = uiState.nickName,
                followersCount = 123123,
                followingCount = 93213,
                recipesCount = 123,
                onSettings = debounce { onSettings() },
                onEditScreen = debounce { onEditProfile() }
            )
        }

        items(uiState.recipes) { recipe ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(recipe.recipeName)
                Text(recipe.createdAt.toString())
            }
        }
    }
}