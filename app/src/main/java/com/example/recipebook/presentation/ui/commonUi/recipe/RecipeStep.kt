package com.example.recipebook.presentation.ui.commonUi.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.LimitedTextField
import com.example.recipebook.presentation.ui.commonUi.RecipeStepImage
import com.example.recipebook.presentation.ui.commonUi.SecondaryText
import com.example.recipebook.presentation.ui.commonUi.SingleActionText
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox

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


@Composable
@Suppress
fun RecipeStepBox(
    index: Int,
    imageSource: String?,
    titleValue: String,
    descriptionValue: String,
    onImageChange: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: () -> Unit,
    onDeleteClick: () -> Unit,
    onCancelImageClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyMediumText(
                modifier = Modifier.width(16.dp),
                text = (index + 1).toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            if (imageSource == null) {
                UploadImageBox(
                    text = null,
                    modifier = Modifier.size(70.dp),
                    onClick = onImageChange,
                    cornerShapeDp = 10.dp
                )
            } else {
                RecipeStepImage(
                    imageSource = imageSource,
                    contentDescription = stringResource(R.string.recipe_step_image),
                    onCancelClick = onCancelImageClick
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.trash_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDeleteClick
                    )
            )
        }

        LimitedTextField(
            value = titleValue,
            onValueChange = onTitleChange,
            onClearText = { onTitleChange("") },
            textLengthLimit = 100,
            hint = stringResource(R.string.step_title_hint),
            isError = false,
            modifier = Modifier.padding(start = 12.dp)
        )

        SingleActionText(
            value = descriptionValue,
            hint = "Description",
            isError = false,
            contentDescription = stringResource(R.string.recipe_step_description),
            onClick = onDescriptionChange,
            painter = null,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}