package com.nonggle.feature.download.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import com.nonggle.feature.download.api.DownLoadNavKey

fun EntryProviderScope<NavKey>.downLoadEntryProvider(navigator: Navigator) {
    entry<DownLoadNavKey> {
        DownloadScreen()
    }
}