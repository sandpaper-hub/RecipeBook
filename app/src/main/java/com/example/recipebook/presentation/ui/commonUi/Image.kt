package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.R

@Composable
@Suppress("FunctionName")
fun ProfileAvatar(
    imageUrl: Any?,
    contentDescription: String,
    size: Dp,
    modifier: Modifier
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(size = size)
                .clip(CircleShape)
        )
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.profile_image,
            placeholder = painterResource(R.drawable.profile_image),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Suppress("FunctionName")
fun ProfileBanner(
    imageUrl: String?,
    contentDescription: String,
    modifier: Modifier
) {
    AsyncImage(
        model = imageUrl ?: R.drawable.profile_image,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.profile_image),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .blur(10.dp),
        contentScale = ContentScale.Crop
    )
}