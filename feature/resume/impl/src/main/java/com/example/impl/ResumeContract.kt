package com.example.feature.resume.impl

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

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

// --- 각 탭 화면에 필요한 데이터 모델 정의 ---
data class InfoData(val profileImageUrl: String, val name: String, val introduction: String)
data class CareerItem(val companyName: String, val role: String, val startDate: String, val endDate: String?, val description: String)
data class PortfolioItem(val title: String, val description: String, val url: String?, val skills: List<String>)
data class ConditionData(val desiredJob: String, val desiredSalary: String)

// --- UI 상태, 이벤트, 이펙트 정의 ---

// 2. 이력서 화면의 전체적인 UI 상태 (훨씬 단순해짐)
data class ResumeState(
    val selectedTab: ResumeTab = ResumeTab.INFO,
    val tabList: List<Int> = listOf(R.string.resume_basicTitle, R.string.resume_careerTitle, R.string.resume_portfolioTitle, R.string.resume_conditionTitle)
) : UiState

// 3. 각 탭 화면의 상태를 나타내는 Sealed Interface
sealed interface ResumeTabState {
    data class Info(val data: InfoData) : ResumeTabState
    data class Career(val items: List<CareerItem>) : ResumeTabState
    data class Portfolio(val items: List<PortfolioItem>) : ResumeTabState
    data class Condition(val data: ConditionData) : ResumeTabState
    data class Error(val message: String) : ResumeTabState
}



sealed interface ResumeEvent: UiEvent {

}

sealed interface ResumeEffect: UiEffect {

}