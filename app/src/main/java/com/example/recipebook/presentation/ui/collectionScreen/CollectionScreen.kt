package com.example.recipebook.presentation.ui.collectionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionCard

@Composable
@Suppress("FunctionName")
fun CollectionScreen() {
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
            items(20) {
                CollectionCard(
                    "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/collections%2Ft5hUCPBLlWr73y1ByweP%2Fcover%2Fcollection_cover.jpg?alt=media&token=2aa45870-3987-43c5-911f-2a234f5ad792",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}