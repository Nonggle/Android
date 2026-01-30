package com.example.feature.resume.impl.step1

import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState

enum class Gender(val value: Int) {
    MALE(0), FEMALE(1);

    companion object {
        private val map = entries.associateBy(Gender::value)
        fun getByValue(value: Int): Gender? {
            return map[value]
        }
    }
}

data class InfoData(
    val profileImageUrl: String? = null,
    val name: String? = null,
    val introduction: String? = null
)

data class ResumeStep1State(
    val isLoading: Boolean = true,
    val info: InfoData = InfoData()
) : UiState

sealed interface ResumeStep1Event : UiEvent {

}

sealed interface ResumeStep1Effect : UiEffect {

}