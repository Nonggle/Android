package com.nonggle.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.designsystem.theme.soYo
import com.nonggle.core.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleTopAppBar(
    @StringRes titleRes: Int? = null,
    @DrawableRes navigationIcon: Int,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            if (titleRes == null) {
            } else Text(text = stringResource(id = titleRes))
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Image(
                    painter = painterResource(id = navigationIcon),
                    contentDescription = null,
                )
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier.testTag("nonggleTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleMainTopAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = NonggleTheme.colorScheme.m1,
            titleContentColor = NonggleTheme.colorScheme.m1,
            actionIconContentColor = NonggleTheme.colorScheme.m1,
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = NonggleTheme.colorScheme.m1,
                style = TextStyle(
                    fontFamily = soYo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun NonggleTopAppBarPreview() {
    NonggleTheme {
        NonggleTopAppBar(
            titleRes = R.string.app_name,
            navigationIcon = R.drawable.close,
            actions = {
                Row {
                    NonggleIconButton(
                        onClick = {},
                        image = painterResource(R.drawable.close),
                    )
                }
            }
        )
    }
}

@Preview("Top App Bar")
@Composable
private fun NonggleMainTopAppBarPreview() {
    NonggleTheme {
        NonggleMainTopAppBar()
    }
}
