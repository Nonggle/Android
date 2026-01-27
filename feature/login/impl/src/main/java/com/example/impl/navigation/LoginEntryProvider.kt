package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.LoginNavKey
import com.example.impl.LoginScreen

fun EntryProviderScope<NavKey>.LoginEntryProvider(navigateToMain: () -> Unit) {
    entry<LoginNavKey> {
        LoginScreen(
            navigateToMain = navigateToMain,
        )
    }
}