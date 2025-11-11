package com.example.recipebook.ui.onboardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.recipebook.R

@Composable
@Suppress("FunctionName")
fun OnboardingScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = "onboarding image",
            modifier = Modifier.padding(innerPadding),
            contentScale = ContentScale.FillBounds
        )
    }
}