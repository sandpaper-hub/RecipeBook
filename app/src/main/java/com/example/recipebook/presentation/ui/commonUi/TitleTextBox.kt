package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.presentation.util.dashBorder


@Composable
@Suppress("FunctionName")
fun ClickableProfileBox(
    imageUrl: String?,
    fullName: String,
    nickName: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.profile_image),
            size = 82.dp,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = fullName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )

            BodyMediumText(
                modifier = Modifier.padding(top = 4.dp),
                text = "@$nickName",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.details_icon),
            contentDescription = stringResource(R.string.details),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
@Suppress("FunctionName")
fun UploadImageBox(
    text: String?,
    modifier: Modifier,
    cornerShapeDp: Dp,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .dashBorder(
                color = MaterialTheme.colorScheme.inversePrimary,
                strokeWidth = 1.dp,
                dashWidth = 8.dp,
                gapWidth = 4.dp,
                shape = RoundedCornerShape(cornerShapeDp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(R.drawable.photo_icon),
            contentDescription = stringResource(R.string.photo_icon),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        if (text != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun TitleTextFieldBox(
    title: String,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    textHint: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BodyMediumText(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        CustomTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            hint = textHint,
            isError = isError,
            modifier = Modifier
        )
    }
}

@Composable
fun LimitedTextFieldBox(
    title: String,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    textLengthLimit: Int,
    textHint: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BodyMediumText(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )

        LimitedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            onClearText = { onClearText() },
            textLengthLimit = textLengthLimit,
            hint = textHint,
            isError = isError,
            modifier = Modifier
        )
    }
}

@Composable
fun SingleActionTextBox(
    title: String,
    value: String,
    hint: String,
    isError: Boolean,
    contentDescription: String,
    onClick: () -> Unit,
    painter: Painter?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BodyMediumText(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )

        SingleActionText(
            value = value,
            hint = hint,
            isError = isError,
            contentDescription = contentDescription,
            onClick = onClick,
            painter = painter,
            modifier = Modifier
        )
    }
}