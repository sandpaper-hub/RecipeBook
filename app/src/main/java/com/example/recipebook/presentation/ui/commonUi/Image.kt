package com.example.recipebook.presentation.ui.commonUi

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.recipebook.R
import com.example.recipebook.theme.DarkModeBodyColor

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
fun ImageBanner(
    imageUrl: String?,
    contentDescription: String,
    modifier: Modifier
) {
    AsyncImage(
        model = imageUrl ?: R.drawable.profile_image,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.profile_image),
        modifier = modifier
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}


@Composable
@Suppress("FunctionName")
fun ImageCover(
    imageUri: Uri?,
    contentDescription: String,
    onCancelClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.TopEnd
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUri,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        FilledIconButton(
            onClick = onCancelClick,
            modifier = Modifier
                .size(36.dp)
                .padding(top = 12.dp, end = 12.dp),
            colors = IconButtonColors(
                containerColor = DarkModeBodyColor,
                contentColor = Color.White,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.cancel_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun RecipeStepImage(
    imageUri: Uri?,
    contentDescription: String,
    onCancelClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .size(70.dp)
    ) {
        val (image, cancelButton) = createRefs()

        AsyncImage(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(image) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
            model = imageUri,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        FilledIconButton(
            onClick = onCancelClick,
            modifier = Modifier
                .constrainAs(cancelButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .size(20.dp),
            colors = IconButtonColors(
                containerColor = DarkModeBodyColor,
                contentColor = Color.White,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.cancel_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }

    }
}