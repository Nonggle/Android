package com.example.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R
import com.example.core.designsystem.theme.NonggleTheme

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
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(roundedCorner ?: 0.dp),
        border = border,
        contentPadding = contentPadding ?: PaddingValues(all = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disableBackGroundColor ?: backgroundColor,
            disabledContentColor = disableContentColor ?: contentColor,
        ),
    ) {
        content()
    }
}

@Composable
fun FullButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    title: String,
    titleTextStyle: TextStyle = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.white),
) {
    NonggleButton(
        modifier = modifier.clip(RoundedCornerShape(4.dp)),
        enabled = enabled,
        contentColor = NonggleTheme.colorScheme.white,
        backgroundColor = NonggleTheme.colorScheme.m1,
        disableBackGroundColor = NonggleTheme.colorScheme.m3,
        disableContentColor = NonggleTheme.colorScheme.white,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 20.dp),
        content = {
            Text(
                text = title,
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
    titleTextStyle: TextStyle = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.white),
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 13.dp),
    backgroundColor: Color = NonggleTheme.colorScheme.m1,
    contentColor: Color = NonggleTheme.colorScheme.white,
    disableBackGroundColor: Color = NonggleTheme.colorScheme.m3,
    disableContentColor: Color = NonggleTheme.colorScheme.white
) {
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = contentColor,
        roundedCorner = 4.dp,
        backgroundColor = backgroundColor,
        disableContentColor = disableContentColor,
        disableBackGroundColor = disableBackGroundColor,
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
    isSelect: Boolean,
    enabled: Boolean = true,
    titleText: String,
    onClick: () -> Unit,
) {
    NonggleButton(
        modifier = modifier.wrapContentHeight(),
        enabled = enabled,
        contentColor = NonggleTheme.colorScheme.m1,
        disableContentColor = NonggleTheme.colorScheme.g3,
        roundedCorner = 4.dp,
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = if (isSelect) NonggleTheme.colorScheme.m1 else NonggleTheme.colorScheme.g_line),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        content = {
            Text(
                textAlign = TextAlign.Center,
                text = titleText,
                style = NonggleTheme.typography.b4_btn.copy(color = if (isSelect) NonggleTheme.colorScheme.m1 else NonggleTheme.colorScheme.g3)
            )
        }
    )
}

@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color = NonggleTheme.colorScheme.g3,
    disableContentColor: Color = NonggleTheme.colorScheme.g3,
    borderColor: Color = NonggleTheme.colorScheme.g3,
    titleText: String,
    titleTextStyle: TextStyle = NonggleTheme.typography.b1_main.copy(color = contentColor),
    icon: Painter = painterResource(id = R.drawable.right_small),
    onClick: () -> Unit
) {
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = contentColor,
        disableContentColor = disableContentColor,
        roundedCorner = 4.dp,
        backgroundColor = NonggleTheme.colorScheme.white,
        border = BorderStroke(width = 1.dp, color = borderColor),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        content = {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleText,
                    style = titleTextStyle
                )
                Spacer(modifier.weight(1f))
                Icon(
                    painter = icon,
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
    onClick: () -> Unit,
    image: Painter,
    iconWidth: Dp = 20.dp,
    iconHeight: Dp = 20.dp,
    iconColor: Color = NonggleTheme.colorScheme.g3,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(width = iconWidth, height = iconHeight),
    ) {
        Icon(
            modifier = Modifier.size(width = iconWidth, height = iconHeight),
            painter = image,
            tint = iconColor,
            contentDescription = null,
        )
    }
}

@Preview("NonggleButton (Light)", showBackground = true)
@Composable
private fun NonggleButtonPreview() {
    NonggleTheme {
        NonggleButton(
            contentColor = NonggleTheme.colorScheme.white,
            backgroundColor = NonggleTheme.colorScheme.m1,
            onClick = { /*TODO*/ }
        ) {
            Text("Nonggle Button")
        }
    }
}

// --- FullButton Previews ---

@Preview("FullButton Enabled (Light)", showBackground = true)
@Composable
private fun FullButtonEnabledPreview() {
    NonggleTheme {
        FullButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            onClick = { },
            title = "Enabled Full Button"
        )
    }
}

@Preview("FullButton Disabled (Light)", showBackground = true)
@Composable
private fun FullButtonDisabledPreview() {
    NonggleTheme {
        FullButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            onClick = { },
            title = "Disabled Full Button"
        )
    }
}

@Preview("FullButton Enabled (Dark)", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FullButtonEnabledDarkPreview() {
    NonggleTheme(darkTheme = true) {
        FullButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            onClick = { },
            title = "Enabled Full Button"
        )
    }
}

// --- ImageButton Previews ---

@Preview("ImageButton (Light)", showBackground = true)
@Composable
private fun ImageButtonPreview() {
    NonggleTheme {
        ImageButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            titleText = "Login with Google",
            contentColor = Color.Black,
            backgroundColor = Color.White,
            titleTextStyle = NonggleTheme.typography.b4_btn,
            imageResource = R.drawable.right_small
        )
    }
}

// --- ContainedButton Previews ---

@Preview("ContainedButton Enabled (Light)", showBackground = true)
@Composable
private fun ContainedButtonEnabledPreview() {
    NonggleTheme {
        ContainedButton(
            enabled = true,
            onClick = { },
            titleText = "Contained Enabled"
        )
    }
}

@Preview("ContainedButton Disabled (Dark)", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ContainedButtonDisabledDarkPreview() {
    NonggleTheme(darkTheme = true) {
        ContainedButton(
            enabled = false,
            onClick = { },
            titleText = "Contained Disabled"
        )
    }
}

// --- OutlinedButton Previews ---

@Preview("OutlinedButton Selected (Light)", showBackground = true)
@Composable
private fun OutlinedButtonSelectedPreview() {
    NonggleTheme {
        OutlinedButton(
            isSelect = true,
            onClick = { },
            titleText = "Selected"
        )
    }
}

@Preview("OutlinedButton Unselected (Light)", showBackground = true)
@Composable
private fun OutlinedButtonUnselectedPreview() {
    NonggleTheme {
        OutlinedButton(
            isSelect = false,
            onClick = { },
            titleText = "Unselected"
        )
    }
}

@Preview("OutlinedButton Selected (Dark)", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OutlinedButtonSelectedDarkPreview() {
    NonggleTheme(darkTheme = true) {
        OutlinedButton(
            isSelect = true,
            onClick = { },
            titleText = "Selected"
        )
    }
}

// --- OutlinedIconButton Previews ---

@Preview("OutlinedIconButton (Light)", showBackground = true)
@Composable
private fun OutlinedIconButtonPreview() {
    NonggleTheme {
        OutlinedIconButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            titleText = "Navigate to Details"
        )
    }
}

// --- NonggleIconButton Previews ---

@Preview("NonggleIconButton (Light)", showBackground = true)
@Composable
private fun NonggleIconButtonPreview() {
    NonggleTheme {
        NonggleIconButton(
            onClick = { },
            image = painterResource(id = R.drawable.right_small)
        )
    }
}