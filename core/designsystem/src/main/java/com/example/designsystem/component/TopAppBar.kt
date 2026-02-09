package com.example.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.designsystem.R
import com.example.core.designsystem.theme.NonggleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleTopAppBar(
    @StringRes titleRes: Int,
    @DrawableRes navigationIcon: Int,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
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
        colors = colors,
        modifier = modifier.testTag("nonggleTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun NiaTopAppBarPreview() {
    NonggleTheme {
        NonggleTopAppBar(
            titleRes = android.R.string.untitled,
            navigationIcon = R.drawable.close,
        )
    }
}
