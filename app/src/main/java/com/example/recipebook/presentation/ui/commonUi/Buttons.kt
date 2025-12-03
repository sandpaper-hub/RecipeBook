package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipebook.theme.GreenAccent

@Composable
@Suppress("FunctionName")
fun SquareRoundedButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color?,
    isLoading: Boolean,
    modifier: Modifier
) {
    Button(
        onClick = onClick, modifier = modifier.then(
            Modifier
                .height(49.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ?: GreenAccent
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
        } else {
            Text(text = text)
        }
    }
}

@Composable
@Suppress("FunctionName")
fun OutlinedIconButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color,
    icon: Painter?,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(text = text, color = textColor)
    }
}

@Composable
@Suppress("FunctionName")
fun RoundedPrimaryButton(
    onClick: () -> Unit,
    text: String,
    isLoading: Boolean,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun CustomIconButton(
    size: Dp,
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(size = size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}