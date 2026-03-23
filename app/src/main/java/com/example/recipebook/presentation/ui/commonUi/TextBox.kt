package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.recipebook.R
import com.example.recipebook.theme.DarkModeBodyColor
import com.example.recipebook.theme.TitleGray

@Composable
@Suppress("FunctionName")
fun SingleActionText(
    value: String,
    hint: String,
    isError: Boolean?,
    contentDescription: String,
    onClick: () -> Unit,
    painter: Painter?,
    modifier: Modifier
) {

    val borderColor by animateColorAsState(
        if (isError == true) {
            MaterialTheme.colorScheme.error
        } else {
            Color.Unspecified
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .border(width = 0.5.dp, color = borderColor, shape = RoundedCornerShape(14.dp))
            .height(48.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        if (painter != null) {
            Icon(
                painter,
                contentDescription = contentDescription
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            maxLines = 1,
            text = value.ifEmpty { hint },
            color = if (value.isEmpty()) TitleGray else MaterialTheme.colorScheme.inversePrimary,
            style = if (value.isEmpty()) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Suppress("FunctionName")
fun IconTextBox(
    icon: Painter,
    contentDescription: String,
    mainText: String,
    detailText: String?,
    isLogout: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = if (!isLogout) DarkModeBodyColor else Color.Red
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = mainText,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (!isLogout) MaterialTheme.colorScheme.onPrimary else Color.Red
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        if (detailText != null) {
            Text(
                text = detailText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        if (!isLogout) {
            Icon(
                painter = painterResource(R.drawable.details_icon),
                contentDescription = stringResource(R.string.details),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

@Composable
@Suppress("FunctionName")
fun IngredientTextBox(
    ingredient: String,
    amount: String,
    measure: String,
    hint: String,
    onBoxClick: () -> Unit,
    onIconClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onBoxClick
            )
    ) {

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = ingredient.ifBlank {
                hint
            },
            modifier = Modifier
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (ingredient.isEmpty()) TitleGray else MaterialTheme.colorScheme.inversePrimary
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = amount,
            modifier = Modifier.widthIn(max = 54.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = measure,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.trash_icon),
            contentDescription = stringResource(R.string.delete_icon),
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onIconClick
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}