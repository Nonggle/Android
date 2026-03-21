package com.nonggle.impl.complete_resume

import com.nonggle.ui.UiEffect
import com.nonggle.ui.UiEvent
import com.nonggle.ui.UiState

data class CompleteResumeState(
    val isLoading: Boolean = true,
    val uploadSuccess: Boolean? = null,
    val id: Long? = null,
): UiState

sealed interface CompleteResumeEvent: UiEvent {
}

sealed interface CompleteResumeEffect: UiEffect {
    data class ShowErrorMessage(val message: String): CompleteResumeEffect
}