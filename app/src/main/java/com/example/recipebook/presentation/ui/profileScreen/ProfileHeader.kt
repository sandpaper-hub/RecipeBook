package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
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
        val (profileBanner, profilePhoto, surface) = createRefs()

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
            followersCount = followersCount,
            followingCount = followingCount,
            recipesCount = recipesCount,
            modifier = Modifier.constrainAs(surface) {
                centerHorizontallyTo(parent)
                top.linkTo(profilePhoto.top)
            }
        )

        ProfileAvatar(
            painter = profileImage,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier.constrainAs(profilePhoto) {
                centerHorizontallyTo(parent)
                bottom.linkTo(profileBanner.bottom)
            }
        )
    }
}