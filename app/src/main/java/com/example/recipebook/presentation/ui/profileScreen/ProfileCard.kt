package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.presentation.util.convertToFollowersFormat

@Composable
@Suppress
fun ProfileCard(
    profileName: String,
    profileNickName: String,
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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "@$profileNickName",
                style = MaterialTheme.typography.labelMedium
            )
        }
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
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = statisticType,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
@Suppress
fun StatisticDivider(modifier: Modifier) {
    VerticalDivider(
        modifier = modifier.then(Modifier.height(46.dp).width(0.5.dp)),
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

