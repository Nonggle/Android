package com.example.setting.navigation
import com.example.core.navigation.Navigator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.setting.SettingScreen

fun EntryProviderScope<NavKey>.settingEntryProvider(navigator: Navigator) {
    entry<SettingNavKey> {
        SettingScreen (
            onBackClick = { navigator.goBack() },
        )
    }
}