package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.LoginNavKey
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.LoginEntry(navigator: Navigator) {
    entry<LoginNavKey> {

    }
}