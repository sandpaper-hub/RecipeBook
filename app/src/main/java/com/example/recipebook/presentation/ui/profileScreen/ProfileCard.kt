package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.util.convertToFollowersFormat

@Composable
@Suppress
fun ProfileCard(
    profileName: String,
    profileNickName: String,
    followersCount: Int,
    followingCount: Int,
    recipesCount: Int,
    modifier: Modifier
) {
    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(top = 74.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = profileName,
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = profileNickName,
                style = MaterialTheme.typography.titleSmall
            )

            ProfileStatistic(
                followersCount = followersCount,
                followingCount = followingCount,
                recipesCount = recipesCount
            )

            OutlinedIconButton(
                onClick = {},
                text = "Edit Profile",
                icon = null,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
            )
        }
    }
}

@Composable
@Suppress
fun ProfileStatistic(
    followersCount: Int,
    followingCount: Int,
    recipesCount: Int
) {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileStatisticItem(
            count = followersCount,
            statisticType = "Followers",
            modifier = Modifier.weight(1f)
        )
        StatisticDivider()
        ProfileStatisticItem(
            count = followingCount,
            statisticType = "Following",
            modifier = Modifier.weight(1f)
        )
        StatisticDivider()
        ProfileStatisticItem(
            count = recipesCount,
            statisticType = "Recipes",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Suppress
fun ProfileStatisticItem(
    count: Int,
    statisticType: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.convertToFollowersFormat(),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = statisticType,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
@Suppress
fun StatisticDivider() {
    VerticalDivider(
        modifier = Modifier.width(0.5.dp),
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

