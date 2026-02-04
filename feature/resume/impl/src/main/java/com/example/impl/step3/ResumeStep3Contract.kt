package com.example.feature.resume.impl.step3

import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import com.example.feature.resume.impl.step1.ResumeStep1Event
import java.util.UUID

@Stable
data class PersonalityTag(
    val id: String = UUID.randomUUID().toString(),
    val personality: String
)

data class ResumeStep3State(
    val isLoading: Boolean = true,
    val introduce: String? = null,
    val personality: String? = null,
    val introduceDetail: String? = null,
    val personalityList: List<PersonalityTag> = emptyList()
): UiState

sealed interface ResumeStep3Event : UiEvent {
    data class IntroduceChanged(val introduce: String): ResumeStep3Event
    data class PersonalityInput(val value: String): ResumeStep3Event

    data object AddPersonalityChip: ResumeStep3Event
    data class RemovePersonalityChip(val id: String): ResumeStep3Event

    data class IntroduceDetailInput(val value: String): ResumeStep3Event

}

sealed interface ResumeStep3Effect : UiEffect {

}