package com.example.recipebook.presentation.ui.commonUi.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.SecondaryText

@Composable
@Suppress("FunctionName")
fun RecipeStep(
    imageUrl: String?,
    title: String,
    description: String
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.recipe_placeholder,
            contentDescription = stringResource((R.string.banner)),
            placeholder = painterResource(R.drawable.recipe_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 16.dp)
        )

        SecondaryText(
            text = description,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.inversePrimary
            ),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
@Suppress("FunctionName")
fun StepsIndicator(
    pagesCount: Int,
    pagerState: PagerState,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagesCount) { index ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .border(
                        width = if (isSelected) 0.5.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .size(if (isSelected) 10.dp else 8.dp)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified,
                        shape = CircleShape
                    )
            )
        }
    }
}