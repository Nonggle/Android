package com.nonggle.feature.download.impl

import androidx.compose.runtime.Stable
import com.nonggle.ui.UiEffect
import com.nonggle.ui.UiEvent
import com.nonggle.ui.UiState
import com.nonggle.model.SingleResume

@Stable
data class DownloadState(
    val isLoading: Boolean = false,
    val resumeList: List<SingleResume> = emptyList(),
    val isError: Boolean = false,
): UiState

sealed interface DownloadEvent: UiEvent {
    object RetryGetResumeList: DownloadEvent
    /// FIXME: 동시에 삭제가 진행된다면?
    data class DeleteResumeItem(val resumeId: Long): DownloadEvent
}

sealed interface DownloadEffect: UiEffect {
    object ShowErrorToastMessage: DownloadEffect

}