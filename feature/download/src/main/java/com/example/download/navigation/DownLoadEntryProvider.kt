package com.example.download.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import com.example.download.DownloadScreen

fun EntryProviderScope<NavKey>.downLoadEntryProvider(navigator: Navigator) {
    entry<DownLoadNavKey> {
        DownloadScreen(
            onBackClicked = { navigator.goBack() }
        )
    }
}
