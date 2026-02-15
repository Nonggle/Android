package com.example.feature.resume_view.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.resume_view.api.ResumeViewNavKey

fun EntryProviderScope<NavKey>.ResumeViewEntryProvider(navigateToMain: () -> Unit) {
    entry<ResumeViewNavKey> {

    }
}