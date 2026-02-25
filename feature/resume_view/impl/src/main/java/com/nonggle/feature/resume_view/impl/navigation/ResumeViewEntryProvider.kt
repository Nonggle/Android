package com.example.feature.resume_view.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import com.nonggle.feature.resume_view.impl.ResumeViewScreen
import com.nonggle.feature.resume_view.impl.ResumeViewViewModel
import com.nonggle.resume_view.api.ResumeViewNavKey

fun EntryProviderScope<NavKey>.resumeViewEntryProvider(navigator: Navigator) {
    entry<ResumeViewNavKey> { key ->
        val viewModel = hiltViewModel<ResumeViewViewModel, ResumeViewViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(key)
            }
        )

        ResumeViewScreen(viewModel = viewModel)
    }
}