package com.nonggle.feature.download.impl

import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

@Stable
data class DownloadState(
    val isLoading: Boolean = false,
): UiState

sealed interface DownloadEvent: UiEvent {

}

sealed interface DownloadEffect: UiEffect {

}