package com.example.recipebook.presentation.ui.commonUi.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SecondaryText

@Composable
@Suppress("FunctionName")
fun CollectionSquareCard(
    name: String,
    count: Int,
    imageUrl: String?,
    onItemClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.collection_background,
            placeholder = painterResource(R.drawable.collection_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        HeadingTextMedium(
            text = name,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )

        SecondaryText(
            text = "$count recipes",
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.inversePrimary
            )
        )
    }
}

@Composable
@Suppress("FunctionName")
fun CollectionListCard(
    clickEnabled: Boolean,
    name: String,
    imageUrl: String?,
    isRecipeContainCollection: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clickable(
                enabled = clickEnabled,
                onClick = onItemClick
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.collection_image),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            HeadingTextMedium(
                text = name
            )

            Spacer(modifier = Modifier.weight(1f))


            Icon(
                painterResource(
                    if (isRecipeContainCollection) {
                        R.drawable.check_circle_filled_icon
                    } else {
                        R.drawable.add_circle_icon
                    }
                ),
                modifier = Modifier
                    .weight(0.5f),
                contentDescription = "",
                tint = if (isRecipeContainCollection) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}