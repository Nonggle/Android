package com.nonggle.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.api.LoginNavKey
import com.nonggle.login.impl.LoginScreen

fun EntryProviderScope<NavKey>.LoginEntryProvider(navigateToMain: () -> Unit) {
    entry<LoginNavKey> {
        LoginScreen(
            navigateToMain = navigateToMain,
        )
    }
}