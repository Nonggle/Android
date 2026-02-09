package com.example.download.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.download.DownloadScreen
import com.example.core.navigation.Navigator

fun EntryProviderScope<NavKey>.downLoadEntryProvider(navigator: Navigator) {
    entry<DownLoadNavKey> {
        DownloadScreen(
            onBackClicked = { navigator.goBack() }
        )
    }
}