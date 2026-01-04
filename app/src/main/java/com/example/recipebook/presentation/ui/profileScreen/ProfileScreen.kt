package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
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
        modifier = Modifier
            .fillMaxSize(),
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

        item {
            Row(
                modifier = Modifier
                    .padding(
                        top = 32.dp, bottom = 24.dp,
                        start = 24.dp, end = 24.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.my_recipes),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${uiState.recipesCount} ${stringResource(R.string.recipes).lowercase()}"
                )
            }
        }

        items(uiState.recipes) { recipe ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(recipe.recipeName)
                Text(recipe.createdAt.toString())
            }
        }
    }
}