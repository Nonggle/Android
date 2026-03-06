package com.nonggle.feature.resume_view.impl.navigation

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import com.nonggle.model.ResumeContents

// 이력서 화면이 열람용, pdf 렌더링용을 구분하는 enum class
enum class ScreenMode {
    SCREEN, PDF
}

enum class Gender(val value: String) {
    MALE("남"), FEMALE("여");

    companion object {
        fun getByName(name: String): String {
            return when (name) {
                "MALE" -> MALE.value
                "FEMALE" -> FEMALE.value
                else -> ""
            }
        }
    }
}

data class ResumeViewState(
    val isLoading: Boolean = true,
    val resumeDetail: ResumeContents? = null,
    val resumeRetry: Boolean = false,
    val writeExternalPermissionGranted: Boolean = false,
    val screenMode: ScreenMode = ScreenMode.SCREEN,
): UiState

sealed interface ResumeViewEvent: UiEvent {
    object RetryGetResumeDetail: ResumeViewEvent

    /// FIXME: 동시에 파일 다운로드가 요청된다면 스레드 관리?
    object DownloadResumeToLocal: ResumeViewEvent

    object ClickBackIconButton: ResumeViewEvent
}

sealed interface ResumeViewEffect: UiEffect {

    object DownLoadPDF: ResumeViewEffect

    object DownLoadSuccess: ResumeViewEffect

    data class DownLoadFailure(val message: String): ResumeViewEffect
    object NavigateToBack: ResumeViewEffect
}