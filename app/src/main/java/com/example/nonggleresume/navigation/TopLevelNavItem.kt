package com.example.nonggleresume.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.designsystem.icon.NonggleIcons
import com.example.download.navigation.DownLoadNavKey
import com.example.home.navigation.HomeNavKey
import com.example.setting.navigation.SettingNavKey
import com.example.home.R as homeResource
import com.example.download.R as downloadResource
import com.example.setting.R as settingResource

data class TopLevelNavItem(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val iconTextId: Int,
    val title: @Composable () -> String
)

val HOME = TopLevelNavItem(
    selectedIconRes = NonggleIcons.homeSelected,
    unselectedIconRes = NonggleIcons.homeUnselected,
    iconTextId = homeResource.string.feature_name,
    title = { stringResource(homeResource.string.feature_name) }
)

val DOWNLOAD = TopLevelNavItem(
    selectedIconRes = NonggleIcons.downloadSelected,
    unselectedIconRes = NonggleIcons.downloadUnselected,
    iconTextId = downloadResource.string.feature_name,
    title = { stringResource(downloadResource.string.feature_name) }
)

val SETTING = TopLevelNavItem(
    selectedIconRes = NonggleIcons.settingSelected,
    unselectedIconRes = NonggleIcons.settingUnselected,
    iconTextId = settingResource.string.feature_name,
    title = { stringResource(settingResource.string.feature_name) }
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    DownLoadNavKey to DOWNLOAD,
    SettingNavKey to SETTING,
)