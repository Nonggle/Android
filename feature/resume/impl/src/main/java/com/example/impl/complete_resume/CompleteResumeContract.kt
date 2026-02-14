package com.example.impl.complete_resume

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

data class CompleteResumeState(
    val isLoading: Boolean = true,
    val uploadSuccess: Boolean? = null,
): UiState

sealed interface CompleteResumeEvent: UiEvent {
    data object NavigateToUserResume: CompleteResumeEvent
    data object NavigateToBack: CompleteResumeEvent
}

sealed interface CompleteResumeEffect: UiEffect {
    data class setUploadSuccess(val uploadSuccess: Boolean): CompleteResumeEffect
}