package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel

@Composable
@Suppress("FunctionName")
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    if (viewModel.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                ProfileHeader(
                    bannerImage = painterResource(R.drawable.onboarding_image),
                    profileImage = painterResource(R.drawable.onboarding_image),
                    profileName = uiState.fullName,
                    profileNickName = uiState.nickName,
                    followersCount = 123123,
                    followingCount = 93213,
                    recipesCount = 123
                )
            }
        }
    }

}