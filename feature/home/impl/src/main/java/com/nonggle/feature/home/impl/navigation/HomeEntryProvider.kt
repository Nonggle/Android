package com.nonggle.feature.home.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.feature.home.impl.HomeScreen
import com.nonggle.api.HomeNavKey
import com.nonggle.navigation.Navigator
import com.nonggle.resume.api.navigateToResume

fun EntryProviderScope<NavKey>.homeEntryProvider(navigator: Navigator) {
    entry<HomeNavKey> {
        HomeScreen(
            navigateToResumeWritingScreen = navigator::navigateToResume,
        )
    }
}