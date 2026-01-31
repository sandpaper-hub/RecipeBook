package com.example.recipebook.presentation.ui.recipeDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.ImageBanner
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleTextLarge
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeDescription
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeIngredients
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.RecipeDetailViewModel

@Composable
@Suppress("FunctionName")
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(58.dp)
        ) {
            Spacer(modifier = Modifier.width(24.dp))

            ClickableIcon(
                painter = painterResource(R.drawable.back_arrow_icon),
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier,
                onClick = {}
            )

            Spacer(modifier = Modifier.weight(1f))

            ClickableIcon(
                painter = painterResource(R.drawable.more_vert_icon),
                contentDescription = stringResource(R.string.more_action_button),
                modifier = Modifier,
                onClick = {}
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            val (recipeImage, recipeNameText, ingredientBox, descriptionText, ingredientsText,
                letsCookButton) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            ImageBanner(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/recipes%2F4cf22693-f289-4852-b90b-1195c8ede6d6%2Fcover%2Frecipe_cover.jpg?alt=media&token=17e685f4-a6e8-4b38-9202-5115df0bc551",
                contentDescription = stringResource(R.string.recipe_image),
                modifier = Modifier
                    .height(250.dp)
                    .constrainAs(recipeImage) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(parent.top)
                    }
            )



            TitleTextLarge(
                text = "Vegetable pasta",
                modifier = Modifier.constrainAs(recipeNameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(recipeImage.bottom, margin = 24.dp)
                }
            )

            RecipeDescription(
                timeEstimation = "1 hour",
                descriptionText = "",
                modifier = Modifier.constrainAs(descriptionText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(recipeNameText.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                })

            Text(
                text = "${stringResource(R.string.ingredients)} (10)",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .constrainAs(ingredientsText) {
                        start.linkTo(startGuideline)
                        top.linkTo(descriptionText.bottom, margin = 29.dp)
                    }
            )

            RecipeIngredients(
                ingredients = listOf(

                ),
                modifier = Modifier.constrainAs(ingredientBox) {
                    linkTo(start = startGuideline, end = endGuideline)
                    linkTo(
                        top = ingredientsText.bottom,
                        bottom = letsCookButton.top,
                        topMargin = 24.dp,
                        bottomMargin = 24.dp
                    )
                    width = Dimension.fillToConstraints
                }
            )

            SquareRoundedButton(
                onClick = {},
                text = stringResource(R.string.lets_cook),
                isLoading = false,
                modifier = Modifier
                    .constrainAs(letsCookButton) {
                        linkTo(start = startGuideline, end = endGuideline)
                        bottom.linkTo(parent.bottom, margin = 55.dp)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}