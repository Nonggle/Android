package com.nonggle.nonggleresume.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nonggle.designsystem.icon.NonggleIcons
import com.nonggle.api.HomeNavKey
import com.nonggle.nonggleresume.R
import com.nonggle.feature.download.api.DownLoadNavKey
import com.nonggle.mypage.api.MyPageNavKey

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

val MYPAGE = TopLevelNavItem(
    selectedIconRes = NonggleIcons.settingSelected,
    unselectedIconRes = NonggleIcons.settingUnselected,
    title = { stringResource(R.string.nav_item_setting) }
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    DownLoadNavKey to DOWNLOAD,
    MyPageNavKey to MYPAGE,
)
