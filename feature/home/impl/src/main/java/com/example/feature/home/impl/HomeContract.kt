package com.example.feature.home.impl

import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import java.time.LocalTime

@Stable
data class DownloadResume(
    val title: String = "",
    val updatedTime: LocalTime? = null,
)

data class HomeState(
    val downloadResume: DownloadResume = DownloadResume(),
    val userName: String = "", // 서버연동 이후 닉네임 사용할 수 있도록 수정
    val progress: Float = 0f,
    val updateTime: String = "",
    val downloadResumeExist: Boolean = false, // 서버 연동 이후 사용 예정
) : UiState

sealed interface HomeEvent : UiEvent {
    data object NavigateToResumeWritingScreen : HomeEvent
}

sealed interface HomeEffect : UiEffect {

    data object NavigateToResumeWritingScreen : HomeEffect
}
