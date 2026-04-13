package com.example.recipebook.presentation.ui.cooking_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeStep
import com.example.recipebook.presentation.ui.commonUi.recipe.StepsIndicator
import com.example.recipebook.presentation.viewModel.cookingScreen.model.CookingEvent
import com.example.recipebook.presentation.viewModel.cookingScreen.CookingViewModel

@Composable
@Suppress("FunctionName")
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { uiState.recipeSteps.size }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CookingEvent.GoToPage -> pagerState.animateScrollToPage(event.index)
                is CookingEvent.GoBack -> onBack()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(58.dp)
        ) {
            Spacer(modifier = Modifier.width(24.dp))

            ClickableIcon(
                painter = painterResource((R.drawable.back_arrow_icon)),
                contentDescription = stringResource(R.string.back_button),
                onClick = { viewModel.onBack() }
            )

            Spacer(modifier = Modifier.weight(1f))


            Box(contentAlignment = Alignment.BottomCenter) {
                ClickableIcon(
                    painter = painterResource(R.drawable.list_pages_icon),
                    contentDescription = stringResource(R.string.list_pages),
                    onClick = { viewModel.expandPagesMenu(true) }
                )

                AppDropdownMenu(
                    expanded = uiState.isPagesMenuExpanded,
                    items = uiState.recipeSteps,
                    itemContent = { step ->
                        Text(
                            text = step.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    onItemClick = { step ->
                        viewModel.goToPage(step.index)
                        viewModel.expandPagesMenu(false)
                    },
                    onDismiss = { viewModel.expandPagesMenu(false) }
                )
            }

            Spacer(modifier = Modifier.width(24.dp))
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
            modifier = Modifier.weight(1f)
        ) { index ->
            RecipeStep(
                imageUrl = uiState.recipeSteps[index].imageUrl,
                title = uiState.recipeSteps[index].title,
                description = uiState.recipeSteps[index].description
            )
        }

        StepsIndicator(
            pagesCount = uiState.recipeSteps.size,
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}