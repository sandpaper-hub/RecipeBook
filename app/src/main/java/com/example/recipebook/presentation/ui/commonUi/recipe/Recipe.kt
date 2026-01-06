package com.example.recipebook.presentation.ui.commonUi.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R

@Composable
@Suppress("FunctionName")
fun RecipeCardList(
    imageUrl: Any?,
    category: String,
    name: String,
    timeEstimation: String,
    uploadedTime: Long,
) {
    Row {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = imageUrl ?: R.drawable.recipe_placeholder,
                placeholder = painterResource(R.drawable.recipe_placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(modifier = Modifier.padding(vertical = 13.dp)) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = timeEstimation,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = uploadedTime.toString(),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}