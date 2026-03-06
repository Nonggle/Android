package com.nonggle.feature.download.impl

import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import com.nonggle.model.SingleResume

@Stable
data class DownloadState(
    val isLoading: Boolean = false,
    val resumeList: List<SingleResume> = emptyList(),
    val isError: Boolean = false,
): UiState

sealed interface DownloadEvent: UiEvent {
    data object RetryGetResumeList: DownloadEvent
    data class deleteResumeItem(val resumeId: Long): DownloadEvent
}

sealed interface DownloadEffect: UiEffect {
    data object ShowErrorToastMessage: DownloadEffect
}