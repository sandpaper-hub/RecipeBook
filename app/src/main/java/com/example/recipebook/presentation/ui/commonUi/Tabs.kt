package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@Composable
@Suppress("FunctionName")
fun Tabs(
    titles: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    tabContents: List<@Composable () -> Unit>,
    modifier: Modifier
) {

    Column(modifier = modifier) {
        SecondaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Unspecified
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                )
            }
        }

        AnimatedContent(
            targetState = selectedTab,
            contentKey = { it }) { tab ->
            tabContents[tab]()
        }
    }
}