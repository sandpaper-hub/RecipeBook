package com.example.recipebook.ui.createAccountScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.ui.CustomTextField
import com.example.recipebook.ui.SubHeadingClickableText

@Composable
@Suppress
fun CreateAccountScreen() {

    var name by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val (headingText, subHeadingText, fullNameText, nameTextField) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            Text(
                "Create Account",
                modifier = Modifier
                    .constrainAs(headingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 24.dp),
                style = MaterialTheme.typography.headlineMedium)

            SubHeadingClickableText(
                "Enter your name, email and password\nfor sign up. ", "Already have account?",
                Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom)
                    }
                    .padding(top = 12.dp)
            )

            Text(
                "Full Name", modifier = Modifier
                    .constrainAs(fullNameText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom)
                    }
                    .padding(top = 32.dp),
                style = MaterialTheme.typography.bodyMedium)

            CustomTextField(
                value = name,
                onValueChange = { name = it},
                hint = "Constantine Kim...",
                modifier = Modifier
                    .constrainAs(nameTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(fullNameText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp))
        }
    }
}