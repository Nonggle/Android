package com.nonggle.setting.navigation
import com.nonggle.navigation.Navigator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.setting.SettingScreen

fun EntryProviderScope<NavKey>.settingEntryProvider(navigator: Navigator) {
    entry<SettingNavKey> {
        SettingScreen (
            onBackClick = { navigator.goBack() },
        )
    }
}