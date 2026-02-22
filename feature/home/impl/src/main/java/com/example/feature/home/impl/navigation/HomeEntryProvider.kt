package com.example.feature.home.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.feature.home.api.HomeNavKey
import com.example.core.navigation.Navigator
import com.example.feature.resume.api.navigateToResume

fun EntryProviderScope<NavKey>.homeEntryProvider(navigator: Navigator) {
    entry<HomeNavKey> {
        HomeScreen(
            navigateToResumeWritingScreen = navigator::navigateToResume,
        )
    }
}