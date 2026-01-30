package com.example.feature.resume.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.feature.resume.api.ResumeNavKey
import com.example.feature.resume.impl.main.ResumeMainScreen
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.resumeEntry(navigator: Navigator) {
    entry<ResumeNavKey> {
        ResumeMainScreen(
            navigateToHome = { navigator.goBack() },
        )
    }
}