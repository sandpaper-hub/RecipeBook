package com.example.recipebook.presentation.ui.settingsScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.presentation.ui.commonUi.ClickableProfileBox
import com.example.recipebook.presentation.ui.commonUi.HeadingLargeText
import com.example.recipebook.presentation.ui.commonUi.IconTextBox
import com.example.recipebook.presentation.ui.commonUi.SubheadingBackgroundText
import com.example.recipebook.presentation.viewModel.settingsScreen.SettingsViewModel
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.fromLocaleCode
import com.example.recipebook.presentation.viewModel.settingsScreen.model.SettingsEvent

@Composable
@Suppress("FunctionName")
fun SettingsScreen(
    onAccountScreen: () -> Unit,
    onLanguageScreen: () -> Unit,
    onThemeScreen: () -> Unit,
    onLogout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                SettingsEvent.OnAccount -> onAccountScreen()
                SettingsEvent.OnLanguage -> onLanguageScreen()
                SettingsEvent.OnTheme -> onThemeScreen()
                SettingsEvent.OnLogout -> onLogout()
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (headingText, subheadingProfileText, profileBox, subheadingSettingsText,
            languageBox, themeBox, helpBox, logoutBox) = createRefs()

        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingLargeText(
            text = stringResource(R.string.settings_text),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        SubheadingBackgroundText(
            text = stringResource(R.string.profile_text),
            modifier = Modifier
                .constrainAs(subheadingProfileText) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(headingText.bottom, margin = 16.dp)
                }
        )

        ClickableProfileBox(
            imageUrl = uiState.imageUrl,
            fullName = uiState.fullName,
            nickName = uiState.nickName,
            onClick = debounce { viewModel.onAccountScreen() },
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
            icon = painterResource(R.drawable.language_icon),
            contentDescription = stringResource(R.string.language),
            mainText = stringResource(R.string.language),
            detailText = uiState.language?.fromLocaleCode(),
            isLogout = false,
            onClick = debounce { viewModel.onLanguageScreen() },
            modifier = Modifier
                .constrainAs(languageBox) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(subheadingSettingsText.bottom)
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
            onClick = debounce { viewModel.onThemeScreen() },
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