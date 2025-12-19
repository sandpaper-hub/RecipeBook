package com.example.recipebook.presentation.ui.settingsScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.ClickableProfileBox
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.IconTextBox
import com.example.recipebook.presentation.ui.commonUi.SubheadingBackgroundText
import com.example.recipebook.presentation.viewModel.settingsScreen.SettingsViewModel
import com.example.recipebook.util.debounce
import com.example.recipebook.util.fromLocaleCode

@Composable
@Suppress("FunctionName")
fun SettingsScreen(
    onBackNavigation: () -> Unit,
    onAccountScreen: () -> Unit,
    onNotificationScreen: () -> Unit,
    onLanguageScreen: () -> Unit,
    onThemeScreen: () -> Unit,
    onLogout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (backButton, headingText, subheadingProfileText, profileBox, subheadingSettingsText, notificationBox,
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
            onClick = debounce { onBackNavigation() }
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

        ClickableProfileBox(
            imageUrl = uiState.imageUrl,
            fullName = uiState.fullName,
            nickName = uiState.nickName,
            onClick = debounce { onAccountScreen() },
            modifier = Modifier.constrainAs(profileBox) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(subheadingProfileText.bottom)
                width = Dimension.fillToConstraints
            }
        )

        SubheadingBackgroundText(
            text = stringResource(R.string.settings_text),
            modifier = Modifier
                .constrainAs(subheadingSettingsText) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(profileBox.bottom)
                }
        )

        IconTextBox(
            icon = painterResource(R.drawable.notification_icon),
            contentDescription = stringResource(R.string.notification),
            mainText = stringResource(R.string.notification),
            detailText = null,
            isLogout = false,
            onClick = debounce { onNotificationScreen() },
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
            detailText = uiState.language?.fromLocaleCode(),
            isLogout = false,
            onClick = debounce { onLanguageScreen() },
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
            detailText = when (uiState.themeMode) {
                ThemeMode.DARK -> stringResource(R.string.dark_theme)
                ThemeMode.LIGHT -> stringResource(R.string.light_theme)
                ThemeMode.SYSTEM -> stringResource(R.string.system_theme)
            },
            isLogout = false,
            onClick = debounce { onThemeScreen() },
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
            onClick = debounce {},//TODO
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
            onClick = debounce {
                viewModel.logOut()
                onLogout()
            },
            modifier = Modifier
                .constrainAs(logoutBox) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(helpBox.bottom)
                    width = Dimension.fillToConstraints
                }
        )

    }
}