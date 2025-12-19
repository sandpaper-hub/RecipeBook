package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel
import com.example.recipebook.util.debounce

@Composable
@Suppress("FunctionName")
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onSettings: () -> Unit,
    onEditProfile: () -> Unit
) {
    val listState = rememberLazyListState()
    val uiState = viewModel.uiState

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        item {
            ProfileHeader(
                imageUrl = uiState.remoteImageUrl,
                profileName = uiState.fullName,
                profileNickName = uiState.nickName,
                followersCount = 123123,
                followingCount = 93213,
                recipesCount = 123,
                onSettings = debounce { onSettings() },
                onEditScreen = debounce { onEditProfile() }
            )
        }
    }
}