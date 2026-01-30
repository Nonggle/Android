package com.example.feature.resume.impl.step4

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

data class ConditionData(
    val desiredJob: String? = null,
    val desiredSalary: String? = null
)


data class ResumeStep4State(
    val isLoading: Boolean = true,
    val conditionData: ConditionData = ConditionData()
): UiState

sealed interface ResumeStep4Event : UiEvent {

}

sealed interface ResumeStep4Effect : UiEffect {

}