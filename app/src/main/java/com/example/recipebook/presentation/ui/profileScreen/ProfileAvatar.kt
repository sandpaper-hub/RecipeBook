package com.example.recipebook.presentation.ui.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun ProfileAvatar(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier) {
    Box(
        modifier = modifier.then(Modifier
            .size(100.dp)
            .clip(CircleShape)
        )
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}