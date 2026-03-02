package com.example.recipebook.presentation.ui.collectionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionSquareCard
import com.example.recipebook.presentation.viewModel.collectionsScreen.CollectionsViewModel

@Composable
@Suppress("FunctionName")
fun CollectionScreen(
    onCollectionDetail: (String) -> Unit,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (headingText, collectionsGrid) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextLarge(
            text = stringResource(R.string.collections_text),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        LazyVerticalGrid(
            modifier = Modifier.constrainAs(collectionsGrid) {
                linkTo(start = startGuideline, end = endGuideline)
                linkTo(headingText.bottom, bottom = parent.bottom, topMargin = 24.dp)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = uiState.collections, key = { it.id }) { collection ->
                CollectionSquareCard(
                    name = collection.name,
                    count = collection.recipeIds.size,
                    imageUrl = collection.imageSource,
                    onItemClick = { onCollectionDetail(collection.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}