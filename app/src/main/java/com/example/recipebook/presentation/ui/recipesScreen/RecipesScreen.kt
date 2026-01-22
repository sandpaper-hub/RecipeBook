package com.example.recipebook.presentation.ui.recipesScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.Tabs
import com.example.recipebook.presentation.ui.recipesScreen.tabs.DraftTab
import com.example.recipebook.presentation.ui.recipesScreen.tabs.RecipesTab

@Composable
@Suppress("FunctionName")
fun RecipesScreen(onRecipeDetail: (recipeId: String) -> Unit) {

    val draftListState = rememberLazyListState()
    val recipeListState = rememberLazyListState()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    val tabsTitle = listOf(
        stringResource(R.string.my_recipes),
        stringResource(R.string.draft)
    )

    val tabContents: List<@Composable () -> Unit> = listOf(
        {
            RecipesTab(
                listState = recipeListState,
                onRecipeDetail = onRecipeDetail
            )
        },
        { DraftTab(draftListState) }
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (headingText, contentTab) = createRefs()

        HeadingTextLarge(
            text = stringResource(R.string.recipes),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        Tabs(
            titles = tabsTitle,
            tabContents = tabContents,
            onTabSelected = { selectedTab = it },
            selectedTab = selectedTab,
            modifier = Modifier
                .constrainAs(contentTab) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(headingText.bottom, margin = 17.dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })
    }
}