package com.example.recipebook.presentation.ui.HomeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

@Composable
@Suppress("FunctionName")
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            val (text) = createRefs()
            val user = FirebaseAuth.getInstance().currentUser

        Text(user?.displayName?:"No name", modifier = Modifier.constrainAs(text){
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            })
        }
    }
}