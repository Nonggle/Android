package com.example.impl.complete_resume

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

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