package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.recipebook.R

@Composable
@Suppress("FunctionName")
fun ProfileHeader(
    bannerImage: Painter,
    profileImage: Painter,
    profileName: String,
    profileNickName: String,
    followersCount: Int,
    followingCount: Int,
    recipesCount: Int
//    recipeList: TODO
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (profileBanner, profilePhoto, profileCard, followersCountItem,
            followingCountItem, recipesCountItem, divider1, divider2) = createRefs()
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

        ProfileAvatar(
            painter = profileImage,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .constrainAs(profilePhoto) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(profileBanner.bottom)
                }
        )
    }
}