package com.example.recipebook.presentation.ui.uploadScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.Tabs
import com.example.recipebook.presentation.ui.uploadScreen.tabs.DraftTab
import com.example.recipebook.presentation.ui.uploadScreen.tabs.RecipesTab

@Composable
@Suppress("FunctionName")
fun UploadScreen() {

    val draftListState = rememberLazyListState()
    val recipeListState = rememberLazyListState()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    val tabsTitle = listOf(
        stringResource(R.string.draft),
        stringResource(R.string.my_recipes)
    )

    val tabContents: List<@Composable () -> Unit> = listOf(
        { DraftTab(draftListState) },
        { RecipesTab(recipeListState) }
    )

    val currentListState = when (selectedTab) {
        0 -> draftListState
        else -> recipeListState
    }

    val isFabVisible by remember(currentListState) {
        derivedStateOf {
            currentListState.firstVisibleItemIndex < 2
        }
    }

    val isExpanded by remember(currentListState) {
        derivedStateOf {
            currentListState.firstVisibleItemIndex == 0 &&
                    currentListState.firstVisibleItemScrollOffset == 0
        }
    }

    val fabSize by animateDpAsState(
        targetValue = if (isExpanded) 62.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fabSize"
    )

    val fabScale by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.85f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fabScale"
    )

    val fabAlpha by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.7f,
        animationSpec = tween(durationMillis = 150),
        label = "fabAlpha"
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (headingText, contentTab, floatingActionButton) = createRefs()

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

        AnimatedVisibility(
            visible = isFabVisible,
            modifier = Modifier
                .size(fabSize)
                .scale(fabScale)
                .alpha(fabAlpha)
                .constrainAs(floatingActionButton) {
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(parent.bottom, margin = 52.dp)
                }
            ) {

            IconButton(
                onClick = {},
                shape = CircleShape,
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}