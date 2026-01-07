package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.ProfileBanner
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel
import com.example.recipebook.presentation.util.toUpdatedAgoText

@Composable
@Suppress("FunctionName")
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onSettings: () -> Unit,
    onEditProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (profileBanner, profilePhoto, profileCard, followersCountItem,
            followingCountItem, recipesCountItem, divider1, divider2,
            editProfileButton, settingsButton, myRecipesTitle, recipeList) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        createHorizontalChain(
            followersCountItem, divider1, followingCountItem,
            divider2, recipesCountItem, chainStyle = ChainStyle.Spread
        )

        ProfileBanner(
            imageUrl = uiState.profileImageUrl,
            contentDescription = stringResource(R.string.banner_description),
            modifier = Modifier.constrainAs(profileBanner) {
                top.linkTo(parent.top)
            }
        )

        Icon(
            painter = painterResource(R.drawable.settings_icon),
            contentDescription = "Settings button",
            modifier = Modifier
                .constrainAs(settingsButton) {
                    top.linkTo(parent.top)
                    end.linkTo(endGuideline)
                }
                .padding(top = 12.dp)
                .clickable(
                    onClick = onSettings
                )
        )

        ProfileCard(
            profileName = uiState.fullName,
            profileNickName = uiState.nickName,
            modifier = Modifier.constrainAs(profileCard) {
                centerHorizontallyTo(parent)
                top.linkTo(profilePhoto.top)
            }
        )

        ProfileStatisticItem(
            count = 123,
            statisticType = stringResource(R.string.followers),
            modifier = Modifier
                .constrainAs(followersCountItem) {
                    linkTo(
                        start = startGuideline,
                        end = divider1.start
                    )
                    top.linkTo(profileCard.bottom, margin = 32.dp)
                }
        )

        StatisticDivider(
            modifier = Modifier
                .constrainAs(divider1) {
                    linkTo(
                        start = followersCountItem.end,
                        end = followingCountItem.start,
                        top = followersCountItem.top,
                        bottom = followersCountItem.bottom
                    )
                })

        ProfileStatisticItem(
            count = 123,
            statisticType = stringResource(R.string.following),
            modifier = Modifier
                .constrainAs(followingCountItem) {
                    linkTo(
                        start = divider1.end,
                        end = divider2.start,
                        top = followersCountItem.top,
                        bottom = followersCountItem.bottom
                    )
                }
        )

        StatisticDivider(
            modifier = Modifier
                .constrainAs(divider2) {
                    linkTo(
                        start = followingCountItem.end,
                        end = recipesCountItem.start,
                        top = followersCountItem.top,
                        bottom = followersCountItem.bottom
                    )
                }
        )

        ProfileStatisticItem(
            count = uiState.recipesCount,
            statisticType = stringResource(R.string.recipes),
            modifier = Modifier
                .constrainAs(recipesCountItem) {
                    linkTo(
                        start = divider2.end,
                        end = endGuideline,
                        top = followersCountItem.top,
                        bottom = followersCountItem.bottom
                    )
                }
        )

        OutlinedIconButton(
            modifier = Modifier
                .constrainAs(editProfileButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(followersCountItem.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 24.dp),
            onClick = onEditProfile,
            icon = null,
            text = stringResource(R.string.edit_profile),
            textColor = MaterialTheme.colorScheme.inversePrimary)

        ProfileAvatar(
            imageUrl = uiState.profileImageUrl,
            contentDescription = stringResource(R.string.profile_image),
            size = 100.dp,
            modifier = Modifier
                .constrainAs(profilePhoto) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(profileBanner.bottom)
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(myRecipesTitle) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(editProfileButton.bottom, margin = 32.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = stringResource(R.string.my_recipes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = pluralStringResource(
                    R.plurals.recipes,
                    uiState.recipesCount, uiState.recipesCount
                )
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(recipeList) {
                    linkTo(top = myRecipesTitle.bottom, bottom = parent.bottom, topMargin = 24.dp)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            items(uiState.recipes) { recipe ->
                RecipeCardList(
                    imageUrl = recipe.imageUrl,
                    category = recipe.category,
                    name = recipe.recipeName,
                    timeEstimation = recipe.recipeTimeEstimation,
                    uploadedTime = recipe.createdAt.toUpdatedAgoText(),
                )
            }
        }
    }
}