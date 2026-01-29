package com.example.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.NonggleTheme

@Composable
fun NonggleTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    selectedContentColor: Color = NonggleTheme.colorScheme.m1,
    unselectedContentColor: Color = NonggleTheme.colorScheme.g3,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        text = {
            Box(modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)) {
                text()
            }
        }
    )
}

@Composable
fun NonggleTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = NonggleTheme.colorScheme.white,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 2.dp,
                color = NonggleTheme.colorScheme.m1,
            )
        },
        tabs = tabs,
    )
}

@Preview(showBackground = true)
@Composable
fun TabPreview() {
    NonggleTheme {
        val titles = listOf("Tab1", "Tab2", "Tab3")
        NonggleTabRow(selectedTabIndex = 0) {
            titles.forEachIndexed { index, title ->
                NonggleTab(
                    selected = index == 0,
                    onClick = { },
                    text = { Text(text = title) },
                )
            }
        }
    }
}