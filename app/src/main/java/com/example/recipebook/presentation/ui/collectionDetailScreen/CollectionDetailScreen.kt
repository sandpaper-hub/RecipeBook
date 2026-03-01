package com.example.recipebook.presentation.ui.collectionDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.collectionDetailScreen.model.MenuItem
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.DeleteDialog
import com.example.recipebook.presentation.ui.commonUi.TopBarBackNavigation
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionBannerBox
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.util.toUpdatedAgoText
import com.example.recipebook.presentation.viewModel.collectionDetailScreen.CollectionDetailViewModel
import com.example.recipebook.presentation.viewModel.collectionDetailScreen.model.CollectionDetailEvent

@Composable
@Suppress("FunctionName")
fun CollectionDetailScreen(
    onBack: () -> Unit,
    onRecipeDetail: (String) -> Unit,
    onCollectionEdit: (String) -> Unit,
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val menuItems = listOf(
        MenuItem.EDIT,
        MenuItem.DELETE
    )

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CollectionDetailEvent.GoBack -> onBack()
                is CollectionDetailEvent.OnRecipeDetail -> onRecipeDetail(event.recipeId)
                is CollectionDetailEvent.OnCollectionEdit -> onCollectionEdit(event.collectionId)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarBackNavigation(
            onBackClick = { viewModel.onBack() },
            onMenuClick = { viewModel.expandMenu(true) }
        ) {
            AppDropdownMenu(
                expanded = uiState.isMenuExpanded,
                items = menuItems,
                itemContent = { menuItem ->
                    Text(stringResource(menuItem.stringResource))
                },
                onItemClick = { menuItem ->
                    when (menuItem) {
                        MenuItem.EDIT -> {
                            viewModel.onCollectionEdit()
                        }
                        MenuItem.DELETE -> {
                            viewModel.showDeleteDialog(true)
                        }
                    }
                },
                onDismiss = { viewModel.expandMenu(false) }
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                CollectionBannerBox(
                    collectionImage = uiState.imageSource,
                    collectionName = uiState.name,
                    collectionDescription = uiState.description,
                    collectionSize = uiState.collectionSize
                )
            }


            items(uiState.recipeList, key = { it.id }) { recipe ->
                RecipeCardList(
                    recipeId = recipe.id,
                    imageUrl = recipe.imageUrl,
                    categoryResource = R.string.category,
                    name = recipe.recipeName,
                    timeEstimation = recipe.recipeTimeEstimation,
                    uploadedTime = recipe.createdAt.toUpdatedAgoText(),
                    onRecipeClick = { recipeId ->
                        viewModel.onRecipeDetail(recipeId)
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        if (uiState.isDeleteDialogOpen) {
            DeleteDialog(
                headingText = stringResource(R.string.delete_collection),
                warningText = stringResource(R.string.delete_collection_warning_title),
                itemName = uiState.name,
                onDismiss = { viewModel.showDeleteDialog(false) },
                onConfirm = {
                    viewModel.deleteCollection()
                    viewModel.showDeleteDialog(false)
                }
            )
        }
    }
}