package com.example.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.NonggleTheme

@Composable
fun RowScope.NonggleNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NonggleNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NonggleNavigationDefaults.navigationContentColor(),
            selectedTextColor = NonggleNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NonggleNavigationDefaults.navigationContentColor(),
            indicatorColor = NonggleNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun NonggleNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = Color.Green,
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun NonggleMobileNavigationScaffold(
    modifier: Modifier = Modifier,
    navigationBarItems: @Composable RowScope.() -> Unit,
    showBottomBar: Boolean,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {},
        modifier = modifier,
        containerColor = Color.Transparent,
        bottomBar = {
            if(showBottomBar) {
                NonggleNavigationBar {
                    navigationBarItems()
                }
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}

object NonggleNavigationDefaults {
    @Composable
    fun navigationContentColor() = NonggleTheme.colorScheme.m1

    @Composable
    fun navigationSelectedItemColor() = NonggleTheme.colorScheme.m1

    @Composable
    fun navigationIndicatorColor() = NonggleTheme.colorScheme.m1
}