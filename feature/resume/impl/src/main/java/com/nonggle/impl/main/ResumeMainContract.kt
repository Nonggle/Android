package com.nonggle.resume.impl.main

import com.nonggle.ui.UiEffect
import com.nonggle.ui.UiEvent
import com.nonggle.ui.UiState
import com.nonggle.feature.resume.impl.R

// 각 탭을 식별하기 위한 Enum 클래스
enum class ResumeTab(val value: Int) {
    INFO(0),       // 기본 정보
    CAREER(1),     // 경력
    PORTFOLIO(2);  // 포트폴리오

    companion object {
        private val map = entries.associateBy(ResumeTab::value)
        fun getByValue(value:Int): ResumeTab? {
            return map[value]
        }
    }
}

// 이력서 화면의 전체적인 UI 상태
data class ResumeMainState(
    val selectedTab: ResumeTab = ResumeTab.INFO,
    val tabList: List<Int> = listOf(R.string.resume_basicTitle, R.string.resume_careerTitle, R.string.resume_portfolioTitle),
    val submitStatus: Boolean = false
) : UiState

sealed interface ResumeMainEvent: UiEvent {
    data object NavigateToComplete : ResumeMainEvent
}

sealed interface ResumeMainEffect: UiEffect {
    data object NavigateToComplete: ResumeMainEffect
}

