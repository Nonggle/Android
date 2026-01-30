package com.example.feature.resume.impl.main

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import com.example.feature.resume.impl.R

// 각 탭을 명확하게 식별하기 위한 Enum 클래스
enum class ResumeTab(val value: Int) {
    INFO(0),       // 기본 정보
    CAREER(1),     // 경력
    PORTFOLIO(2),  // 포트폴리오
    CONDITION(3);  // 희망 조건

    companion object {
        private val map = ResumeTab.entries.associateBy(ResumeTab::value)
        fun getByValue(value:Int): ResumeTab? {
            return map[value]
        }
    }
}

// 이력서 화면의 전체적인 UI 상태 (훨씬 단순해짐)
data class ResumeMainState(
    val selectedTab: ResumeTab = ResumeTab.INFO,
    val tabList: List<Int> = listOf(R.string.resume_basicTitle, R.string.resume_careerTitle, R.string.resume_portfolioTitle, R.string.resume_conditionTitle)
) : UiState

sealed interface ResumeMainEvent: UiEvent {

}

sealed interface ResumeMainEffect: UiEffect {

}

