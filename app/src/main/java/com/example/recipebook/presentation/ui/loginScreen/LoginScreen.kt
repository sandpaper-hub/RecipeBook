package com.example.recipebook.presentation.ui.loginScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.ClickableText
import com.example.recipebook.presentation.ui.CustomPasswordTextField
import com.example.recipebook.presentation.ui.CustomTextField
import com.example.recipebook.presentation.ui.HeadingText
import com.example.recipebook.presentation.ui.SubHeadingText
import com.example.recipebook.presentation.ui.TitleText

@Composable
@Suppress("FunctionName")
fun LoginScreen() {

    var emailValue: String = ""//TODO
    var passwordValue: String = ""//TODO
    var passwordVisibility: Boolean = false//TODO

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (headingText, subHeadingText, emailText, emailTextField,
                passwordText, passwordTextField, forgotPasswordText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingText(
                text = stringResource(R.string.welcome),
                modifier = Modifier.constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top)
                })

            SubHeadingText(
                text = stringResource(R.string.welcome_subheading),
                modifier = Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom)
                    }
                    .padding(top = 12.dp))

            TitleText(
                text = stringResource(R.string.email),
                modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom)
                    }
                    .padding(top = 32.dp))

            CustomTextField(
                value = emailValue,
                onValueChange = {}, //TODO
                hint = stringResource(R.string.email_hint),
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(emailText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 8.dp))

            TitleText(
                text = stringResource(R.string.password),
                modifier = Modifier
                    .constrainAs(passwordText) {
                        start.linkTo(startGuideline)
                        top.linkTo(emailTextField.bottom)
                    }
                    .padding(top = 20.dp))

            CustomPasswordTextField(
                value = passwordValue,
                onValueChange = {},//todo
                hint = stringResource(R.string.password_hint),
                modifier = Modifier
                    .constrainAs(passwordTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 8.dp),
                visible = passwordVisibility,
                changeVisibility = {}) //todo

            ClickableText("Forgot password?",
                modifier = Modifier.constrainAs(forgotPasswordText){
                    end.linkTo(endGuideline)
                    top.linkTo(passwordTextField.bottom)
                }
                    .padding(top = 25.dp, end = 5.dp))
        }
    }
}