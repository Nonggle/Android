package com.example.feature.resume.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.core.navigation.Navigator
import com.example.feature.resume.api.ResumeNavKey
import com.example.feature.resume.impl.main.ResumeMainScreen
import com.example.impl.complete_resume.CompleteResumeScreen

fun EntryProviderScope<NavKey>.resumeEntryProvider(navigator: Navigator) {

    entry<ResumeNavKey.ResumeWrite> {
        ResumeMainScreen(
            navigateToHome = { navigator.goBack() },
            navigateToComplete = { navigator.navigate(ResumeNavKey.ResumeComplete) }
        )
    }

    entry<ResumeNavKey.ResumeComplete> {
        CompleteResumeScreen(
            navigateToUserResume = {
                /// FIXME: 이력서 열람 화면으로의 이동 추후 구현 예정
            },
            navigateToBack = { navigator.goBack() }
        )
    }
}