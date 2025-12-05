package com.example.recipebook.presentation.ui.settingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.IconTextBox
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.SubHeadingTextSmall
import com.example.recipebook.presentation.ui.commonUi.SubheadingBackgroundText

@Composable
@Suppress("FunctionName")
fun SettingsScreen(
    onBackNavigation: () -> Unit,
    onAccountScreen: () -> Unit,
    onNotificationScreen: () -> Unit,
    onLanguageScreen: () -> Unit,
    onThemeScreen: () -> Unit
) {
    Scaffold { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val (backButton, headingText, subheadingProfileText, profileImage, profileNameText,
                nickNameText, detailsIcon, subheadingSettingsText, notificationBox,
                languageBox, themeBox, helpBox, logoutBox) = createRefs()

            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            ClickableIcon(
                painter = painterResource(R.drawable.back_arrow_icon),
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier
                    .constrainAs(backButton) {
                        start.linkTo(startGuideline)
                        top.linkTo(parent.top, margin = 16.dp)
                    },
                onClick = onBackNavigation
            )

            HeadingTextMedium(
                text = stringResource(R.string.settings_text),
                modifier = Modifier
                    .constrainAs(headingText) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
            )

            SubheadingBackgroundText(
                text = stringResource(R.string.profile_text),
                modifier = Modifier
                    .constrainAs(subheadingProfileText) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(backButton.bottom, margin = 16.dp)
                    }
            )

            ProfileAvatar(
                imageUrl =
                    "https://firebasestorage.googleapis.com/v0/b/recipebook-4b1fd.firebasestorage.app/o/users_avatar%2F3pYTN6tq6LdO5jKvTJA6iDdBPfg1%2Favatar.jpg?alt=media&token=9274d8df-ef63-4f12-b88d-ae87fcb5e050",
                contentDescription = stringResource(R.string.profile_image),
                size = 82.dp,
                modifier = Modifier
                    .constrainAs(profileImage) {
                        start.linkTo(startGuideline)
                        top.linkTo(subheadingProfileText.bottom, margin = 24.dp)
                    })

            Text(
                text = stringResource(R.string.full_name),
                modifier = Modifier
                    .constrainAs(profileNameText) {
                        start.linkTo(profileImage.end, margin = 16.dp)
                        top.linkTo(profileImage.top, margin = 16.5.dp)
                    },
                style = MaterialTheme.typography.displayLarge
            )

            SubHeadingTextSmall(
                text = stringResource(R.string.nick_name),
                modifier = Modifier
                    .constrainAs(nickNameText) {
                        start.linkTo(profileImage.end, margin = 16.dp)
                        top.linkTo(profileNameText.bottom, margin = 4.dp)
                    }
            )

            Icon(
                painter = painterResource(R.drawable.details_icon),
                contentDescription = stringResource(R.string.details),
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .clickable(onClick = onAccountScreen)
                    .constrainAs(detailsIcon) {
                        end.linkTo(endGuideline)
                        linkTo(top = profileNameText.top, bottom = nickNameText.bottom)
                    }
            )

            SubheadingBackgroundText(
                text = stringResource(R.string.settings_text),
                modifier = Modifier
                    .constrainAs(subheadingSettingsText) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(profileImage.bottom, margin = 24.dp)
                    }
            )

            IconTextBox(
                icon = painterResource(R.drawable.notification_icon),
                contentDescription = stringResource(R.string.notification),
                mainText = stringResource(R.string.notification),
                detailText = null,
                isLogout = false,
                onClick = onNotificationScreen,
                modifier = Modifier
                    .constrainAs(notificationBox) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(subheadingSettingsText.bottom)
                        width = Dimension.fillToConstraints
                    })

            IconTextBox(
                icon = painterResource(R.drawable.language_icon),
                contentDescription = stringResource(R.string.language),
                mainText = stringResource(R.string.language),
                detailText = stringResource(R.string.language),
                isLogout = false,
                onClick = onLanguageScreen,
                modifier = Modifier
                    .constrainAs(languageBox) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(notificationBox.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            IconTextBox(
                icon = painterResource(R.drawable.theme_icon),
                contentDescription = stringResource(R.string.theme),
                mainText = stringResource(R.string.theme),
                detailText = stringResource(R.string.details),
                isLogout = false,
                onClick = { onThemeScreen() },
                modifier = Modifier
                    .constrainAs(themeBox) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(languageBox.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            IconTextBox(
                icon = painterResource(R.drawable.help_icon),
                contentDescription = stringResource(R.string.help),
                mainText = stringResource(R.string.help),
                detailText = null,
                isLogout = false,
                onClick = {},//TODO
                modifier = Modifier
                    .constrainAs(helpBox) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(themeBox.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            IconTextBox(
                icon = painterResource(R.drawable.logout_icon),
                contentDescription = stringResource(R.string.logout),
                mainText = stringResource(R.string.logout),
                detailText = null,
                isLogout = true,
                onClick = {},//TODO
                modifier = Modifier
                    .constrainAs(logoutBox) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(helpBox.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

        }
    }
}