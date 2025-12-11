package com.example.recipebook.presentation.ui.themeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SelectableText
import com.example.recipebook.presentation.viewModel.themeScreen.ThemeViewModel
import com.example.recipebook.util.toTitleString

@Composable
@Suppress("FunctionName")
fun ThemeScreen(
    onBack: () -> Unit,
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val themeModes = listOf(
        ThemeMode.LIGHT,
        ThemeMode.DARK,
        ThemeMode.SYSTEM
    )
    val selectedValue by viewModel.theme.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backButton, headingText, selectableTextBox) = createRefs()
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
            onClick = onBack
        )

        HeadingTextMedium(
            text = stringResource(R.string.theme),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 16.dp)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(selectableTextBox) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(backButton.bottom, margin = 16.dp)
                }
                .selectableGroup()) {
            themeModes.forEach { themeMode ->
                SelectableText(
                    text = themeMode.toTitleString(),
                    selected = themeMode.toString().uppercase() == selectedValue.toString(),
                    onClick = { viewModel.changeTheme(themeMode) }
                )
            }
        }
    }
}