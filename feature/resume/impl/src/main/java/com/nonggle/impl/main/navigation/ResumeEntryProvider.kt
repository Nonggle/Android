package com.nonggle.resume.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.navigation.Navigator
import com.nonggle.impl.complete_resume.CompleteResumeScreen
import com.nonggle.resume.api.ResumeNavKey
import com.nonggle.resume.impl.main.ResumeMainScreen
import com.nonggle.resume_view.api.navigateToResumeView

fun EntryProviderScope<NavKey>.resumeEntryProvider(navigator: Navigator) {

    entry<ResumeNavKey.ResumeWrite> {
        ResumeMainScreen(
            navigateToHome = { navigator.goBack() },
            navigateToComplete = {
                navigator.navigate(ResumeNavKey.ResumeComplete)
            }
        )
    }

    entry<ResumeNavKey.ResumeComplete> {
        CompleteResumeScreen(
            navigateToUserResume = navigator::navigateToResumeView,
            navigateToBack = { navigator.goBack() }
        )
    }
}