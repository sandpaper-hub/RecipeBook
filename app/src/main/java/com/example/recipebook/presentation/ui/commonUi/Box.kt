package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.DarkModeBodyColor

@Composable
@Suppress("FunctionName")
fun IconTextBox(
    icon: Painter,
    contentDescription: String,
    mainText: String,
    detailText: String?,
    isLogout: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = if (!isLogout) DarkModeBodyColor else Color.Red
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = mainText,
            style = MaterialTheme.typography.displayLarge,
            color = if (!isLogout) MaterialTheme.colorScheme.onPrimary else Color.Red
        )

        Spacer(modifier = Modifier.weight(1f))

        if (detailText != null) {
            Text(
                text = detailText,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        if (!isLogout) {
            Icon(
                painter = painterResource(R.drawable.details_icon),
                contentDescription = stringResource(R.string.details),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun ClickableProfileBox(
    imageUrl: String?,
    fullName: String,
    nickName: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.profile_image),
            size = 82.dp,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = fullName,
                style = MaterialTheme.typography.displayLarge
            )

            SubHeadingTextSmall(
                text = "@$nickName",
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.details_icon),
            contentDescription = stringResource(R.string.details),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}