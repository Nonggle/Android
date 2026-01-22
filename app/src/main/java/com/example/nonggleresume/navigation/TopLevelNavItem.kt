package com.example.nonggleresume.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    @StringRes val titleTextId: Int,
)

val HOME = TopLevelNavItem(
    selectedIconRes = NonggleIcons.homeSelected,
    unselectedIconRes = NonggleIcons.homeUnselected,
    iconTextId = homeResource.string.feature_name,
    titleTextId = homeResource.string.feature_name,
)

val DOWNLOAD = TopLevelNavItem(
    selectedIconRes = NonggleIcons.downloadSelected,
    unselectedIconRes = NonggleIcons.downloadUnselected,
    iconTextId = downloadResource.string.feature_name,
    titleTextId = downloadResource.string.feature_name,
)

val SETTING = TopLevelNavItem(
    selectedIconRes = NonggleIcons.homeSelected,
    unselectedIconRes = NonggleIcons.homeUnselected,
    iconTextId = settingResource.string.feature_name,
    titleTextId = settingResource.string.feature_name,
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    DownLoadNavKey to DOWNLOAD,
    SettingNavKey to SETTING,
)