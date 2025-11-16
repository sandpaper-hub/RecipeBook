package com.example.recipebook.presentation.ui.loginScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.HeadingText

@Composable
@Suppress("FunctionName")
fun LoginScreen() {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (headingText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingText(
                text = stringResource(R.string.welcome),
                modifier = Modifier.constrainAs(headingText){
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top)
                }
            )
        }
    }
}