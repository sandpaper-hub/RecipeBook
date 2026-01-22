package com.example.recipebook.presentation.ui.commonUi.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ExpandableText
import com.example.recipebook.presentation.ui.commonUi.IngredientTextBox
import com.example.recipebook.presentation.util.parseIngredient

@Composable
@Suppress("FunctionName")
fun RecipeCardList(
    recipeId: String,
    imageUrl: Any?,
    category: String,
    name: String,
    timeEstimation: String,
    uploadedTime: String,
    onRecipeClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onRecipeClick(recipeId) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
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

        Column {
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
                text = uploadedTime,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun RecipeDescription(
    timeEstimation: String,
    descriptionText: String,
    modifier: Modifier
) {
    Column(modifier = modifier.then(Modifier.fillMaxWidth()),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.time_icon),
                contentDescription = stringResource(R.string.time_estimation),
                tint = MaterialTheme.colorScheme.inversePrimary
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = timeEstimation,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                )

            )
        }

        ExpandableText(
            text = descriptionText
        )
    }
}

@Composable
@Suppress("FunctionName")
fun RecipeIngredients(
    ingredients: List<String>,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        ingredients.forEach { ingredient ->
            val name = ingredient.parseIngredient().first
            val amount = ingredient.parseIngredient().second
            IngredientTextBox(name, amount)
        }
    }
}