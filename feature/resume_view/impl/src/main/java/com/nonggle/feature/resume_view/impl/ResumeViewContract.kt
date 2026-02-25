package com.nonggle.feature.resume_view.impl.navigation

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import com.nonggle.model.ResumeContents

data class ResumeViewState(
    val isLoading: Boolean = true,
    val resumeDetail: ResumeContents? = null,
    val resumeRetry: Boolean = false
): UiState

sealed interface ResumeViewEvent: UiEvent {
    data object RetryGetResumeDetail: ResumeViewEvent
}

sealed interface ResumeViewEffect: UiEffect {

}