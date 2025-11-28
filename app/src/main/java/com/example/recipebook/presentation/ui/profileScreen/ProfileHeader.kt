package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.ProfileBanner

@Composable
@Suppress("FunctionName")
fun ProfileHeader(
    bannerImage: Painter,
    profileImage: Painter,
    profileName: String,
    profileNickName: String,
    followersCount: Int,
    followingCount: Int,
    recipesCount: Int,
    onSettings: () -> Unit,
    onEditScreen: () -> Unit
//    recipeList: TODO
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (profileBanner, profilePhoto, profileCard, followersCountItem,
            followingCountItem, recipesCountItem, divider1, divider2,
            editProfileButton, settingsButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        createHorizontalChain(
            followersCountItem, divider1, followingCountItem,
            divider2, recipesCountItem, chainStyle = ChainStyle.Spread
        )

        ProfileBanner(
            painter = bannerImage,
            contentDescription = stringResource(R.string.banner_description),
            modifier = Modifier.constrainAs(profileBanner) {
                top.linkTo(profileBanner.top)
            }
        )

        Icon(
            painter = painterResource(R.drawable.settings),
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
            profileName = profileName,
            profileNickName = profileNickName,
            modifier = Modifier.constrainAs(profileCard) {
                centerHorizontallyTo(parent)
                top.linkTo(profilePhoto.top)
            }
        )

        ProfileStatisticItem(
            count = followersCount,
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
            count = followingCount,
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
            count = recipesCount,
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
            onClick = onEditScreen,
            icon = null,
            text = "Edit Profile",
            textColor = MaterialTheme.colorScheme.inversePrimary)

        ProfileAvatar(
            painter = profileImage,
            contentDescription = stringResource(R.string.profile_image),
            size = 100.dp,
            modifier = Modifier
                .constrainAs(profilePhoto) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(profileBanner.bottom)
                }
        )
    }
}