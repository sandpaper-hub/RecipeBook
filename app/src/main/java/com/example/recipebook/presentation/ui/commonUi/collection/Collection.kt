package com.example.recipebook.presentation.ui.commonUi.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SecondaryText

@Composable
@Suppress("FunctionName")
fun CollectionCard(
    imageUrl: String?,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.collection_background,
            placeholder = painterResource(R.drawable.collection_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(180.dp)
        )

        HeadingTextMedium(
            text = "CollectionName",
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )

        SecondaryText(
            text = "2 recipes",
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.inversePrimary
            )
        )
    }
}