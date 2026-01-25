package com.example.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.R

@Composable
fun NonggleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color,
    disableContentColor: Color? = null,
    border: BorderStroke? = null,
    roundedCorner: Dp? = null,
    backgroundColor: Color,
    disableBackGroundColor: Color? = null,
    onClick: () -> Unit,
    contentPadding: PaddingValues? = null,
    pressedColor: Color = backgroundColor,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(roundedCorner ?: 0.dp),
        border = border,
        contentPadding = contentPadding ?: PaddingValues(all = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isPressed) pressedColor else backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disableBackGroundColor ?: backgroundColor,
            disabledContentColor = disableContentColor ?: contentColor,
        ),
        interactionSource = interactionSource,
    ) {
        content()
    }
}

@Composable
fun FullButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    titleText: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disableBackGroundColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    titleTextStyle: TextStyle
) {
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = Color.White,
        backgroundColor = backgroundColor,
        disableContentColor = disableBackGroundColor,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 20.dp),
        content = {
            Text(
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun ImageButton(
    modifier: Modifier,
    onClick: () -> Unit,
    titleText: String,
    contentColor: Color,
    backgroundColor: Color,
    titleTextStyle: TextStyle,
    imageResource: Int
) {
    NonggleButton(
        modifier = modifier,
        roundedCorner = 4.dp,
        enabled = true,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 14.dp),
        content = {
            Row(
                modifier = Modifier.wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = imageResource),
                    modifier = Modifier
                        .size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = titleText,
                    style = titleTextStyle
                )
            }
        }
    )
}

@Composable
fun ContainedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    titleText: String,
    titleTextStyle: TextStyle,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 13.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    pressBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    disableBackGroundColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = Color.White,
        roundedCorner = 4.dp,
        pressedColor = pressBackgroundColor,
        backgroundColor = backgroundColor,
        disableContentColor = disableBackGroundColor,
        onClick = onClick,
        contentPadding = contentPadding,
        content = {
            Text(
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enableColor: Color,
    enableContentColor: Color,
    pressedColor: Color,
    disableContentColor: Color? = null,
    titleText: String,
    titleTextStyle: TextStyle,
    onClick: () -> Unit,
) {
    NonggleButton(
        modifier = modifier.wrapContentHeight(),
        enabled = enabled,
        pressedColor = pressedColor,
        contentColor = enableContentColor,
        disableContentColor = disableContentColor,
        roundedCorner = 4.dp,
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = enableColor),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        content = {
            Text(
                textAlign = TextAlign.Center,
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    contentColor: Color,
    pressedContentColor: Color,
    disableContentColor: Color,
    borderColor: Color,
    titleText: String,
    titleTextStyle: TextStyle,
    onClick: () -> Unit
) {
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        pressedColor = pressedContentColor,
        contentColor = contentColor,
        disableContentColor = disableContentColor,
        roundedCorner = 4.dp,
        backgroundColor = borderColor,
        border = BorderStroke(width = 1.dp, color = borderColor),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        content = {
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleText,
                    style = titleTextStyle
                )
                Spacer(modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.right_small),
                    modifier = modifier.size(width = 24.dp, height = 24.dp),
                    tint = contentColor,
                    contentDescription = null,
                )
            }
        }
    )
}

@Composable
fun NonggleIconButton(
    modifier: Modifier = Modifier,
    ImageResourceId: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = ImageResourceId),
            modifier = modifier.size(width = 20.dp, height = 20.dp),
            contentDescription = null,
        )
    }
}