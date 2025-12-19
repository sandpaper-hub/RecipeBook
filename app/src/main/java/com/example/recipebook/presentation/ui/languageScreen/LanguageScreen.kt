package com.example.recipebook.presentation.ui.languageScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SelectableText
import com.example.recipebook.presentation.viewModel.languageScreen.LanguageViewModel
import com.example.recipebook.presentation.viewModel.languageScreen.model.LanguageItem

@Composable
@Suppress("FunctionName")
fun LanguageScreen(
    onBackNavigation: () -> Unit,
    viewModel: LanguageViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState
    val languages = listOf(
        LanguageItem("ru", "Русский"),
        LanguageItem("en", "English")
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backButton, headingText, languageBox) = createRefs()
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
            text = stringResource(R.string.language),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 16.dp)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(languageBox) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(backButton.bottom, margin = 32.dp)
                }
                .selectableGroup()
        ) {
            languages.forEach { language ->
                SelectableText(
                    text = language.label,
                    selected = uiState.language == language.code,
                    onClick = {viewModel.changeApplicationLanguage(language.code)}
                )
            }
        }
    }
}