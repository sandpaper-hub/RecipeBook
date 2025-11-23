package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.recipebook.R

@Composable
@Suppress("FunctionName")
fun ProfileScreen() {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        item {
            ProfileHeader(
                bannerImage = painterResource(R.drawable.onboarding_image),
                profileImage = painterResource(R.drawable.onboarding_image),
                profileName = "User Name",
                profileNickName = "@Username",
                followersCount = 123123,
                followingCount = 93213,
                recipesCount = 123
            )
        }
    }
}