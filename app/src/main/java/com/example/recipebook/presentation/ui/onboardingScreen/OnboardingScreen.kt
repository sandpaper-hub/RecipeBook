package com.example.recipebook.presentation.ui.onboardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.theme.DarkModeBodyColor
import com.example.recipebook.presentation.util.debounce

@Composable
@Suppress("FunctionName")
fun OnboardingScreen(
    onRegistrationScreen: () -> Unit,
    onLoginScreen: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (onboardingImage, headingText, titleText, registerButton, signInButton) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = stringResource(R.string.onboarding_image),
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(onboardingImage) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = stringResource(R.string.main_title),
            modifier = Modifier
                .constrainAs(headingText) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(titleText.top)
                }
                .padding(start = 40.dp, end = 40.dp, bottom = 16.dp),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center, color = Color.White
        )

        Text(
            text = stringResource(R.string.main_subtitle),
            modifier = Modifier
                .constrainAs(titleText) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(registerButton.top)
                }
                .padding(bottom = 16.dp, start = 63.dp, end = 63.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = DarkModeBodyColor
            ))

        SquareRoundedButton(
            onClick = debounce { onRegistrationScreen() },
            stringResource(R.string.sign_up_button),
            isLoading = false,
            modifier = Modifier
                .constrainAs(registerButton) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(signInButton.top)
                }
                .padding(bottom = 8.dp))

        SquareRoundedButton(
            onClick = debounce { onLoginScreen() },
            text = stringResource(R.string.sign_in_button),
            containerColor = Color.Transparent,
            isLoading = false,
            modifier = Modifier
                .constrainAs(signInButton) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                }
                .padding(bottom = 48.dp)
        )
    }
}