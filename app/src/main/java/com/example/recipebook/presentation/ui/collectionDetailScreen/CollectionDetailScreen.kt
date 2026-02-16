package com.example.recipebook.presentation.ui.collectionDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.TopBarBackNavigation
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionBannerBox
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.viewModel.collectionDetailScreen.CollectionDetailViewModel

@Composable
@Suppress("FunctionName")
fun CollectionDetailScreen(
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarBackNavigation(
            onBackClick = {},
            onMenuClick = {}
        ) {
            //DROPDOWN MENU
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                CollectionBannerBox(
                    collectionImage = "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/collections%2FWw2jHgA7QZuU0CZcdns1%2Fcover%2Fcollection_cover.jpg?alt=media&token=bd4d74b1-efbf-460f-ae3b-bf76f94a2f34",
                    collectionName = "123",
                    collectionDescription = "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/collections%2FWw2jHgA7QZuU0CZcdns1%2Fcover%2Fcollection_cover.jpg?alt=media&token=bd4d74b1-efbf-460f-ae3b-bf76f94a2f34",
                    collectionSize = 4
                )
            }

            items(10) {
                RecipeCardList(
                    recipeId = "",
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/collections%2FWw2jHgA7QZuU0CZcdns1%2Fcover%2Fcollection_cover.jpg?alt=media&token=bd4d74b1-efbf-460f-ae3b-bf76f94a2f34",
                    categoryResource = R.string.category,
                    name = "someRecipe",
                    timeEstimation = "2 hour",
                    uploadedTime = "1234",
                    onRecipeClick = {},
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}