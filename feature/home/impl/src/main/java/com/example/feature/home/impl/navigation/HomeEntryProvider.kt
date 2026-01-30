package com.example.feature.home.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.feature.home.api.HomeNavKey
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.HomeEntryProvider(navigator: Navigator) {
    entry<HomeNavKey> {
        HomeScreen(

        )
    }
}