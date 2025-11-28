package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun ProfileAvatar(
    painter: Painter,
    contentDescription: String,
    size: Dp,
    modifier: Modifier) {
    Box(
        modifier = modifier.then(Modifier
            .size(size = size)
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

@Composable
@Suppress("FunctionName")
fun ProfileBanner(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .blur(10.dp)
        )
    )
}