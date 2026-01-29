package com.example.nonggleresume.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.designsystem.icon.NonggleIcons
import com.example.download.navigation.DownLoadNavKey
import com.example.home.navigation.HomeNavKey
import com.example.nonggleresume.R
import com.example.setting.navigation.SettingNavKey

data class TopLevelNavItem(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    val title: @Composable () -> String
)

val HOME = TopLevelNavItem(
    selectedIconRes = NonggleIcons.homeSelected,
    unselectedIconRes = NonggleIcons.homeUnselected,
    title = { stringResource(R.string.nav_item_home) }
)

val DOWNLOAD = TopLevelNavItem(
    selectedIconRes = NonggleIcons.downloadSelected,
    unselectedIconRes = NonggleIcons.downloadUnselected,
    title = { stringResource(R.string.nav_item_download) }
)

val SETTING = TopLevelNavItem(
    selectedIconRes = NonggleIcons.settingSelected,
    unselectedIconRes = NonggleIcons.settingUnselected,
    title = { stringResource(R.string.nav_item_setting) }
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    DownLoadNavKey to DOWNLOAD,
    SettingNavKey to SETTING,
)
