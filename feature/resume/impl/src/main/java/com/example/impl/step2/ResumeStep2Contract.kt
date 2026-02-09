package com.example.feature.resume.impl.step2

import androidx.compose.runtime.Stable
import com.example.core.ui.UiEffect
import com.example.core.ui.UiEvent
import com.example.core.ui.UiState
import java.time.LocalDate
import java.time.Period
import java.util.UUID

@Stable
data class CareerFormData(
    val id: String = UUID.randomUUID().toString(),
    val careerStartDate: LocalDate? = null,
    val careerEndDate: LocalDate? = null,
    val careerDescription: String = "",
    val careerDetail: String = "",
)

data class ResumeStep2State(
    val isLoading: Boolean = true,
    val careerFormData: CareerFormData = CareerFormData(),
    val careerList: List<CareerFormData> = emptyList(),
    val totalCareer: Period = Period.of(0,0,0),
): UiState

sealed interface CareerBottomSheetEvent: UiEvent {
    data class CareerDescriptionInput(val description: String): CareerBottomSheetEvent
    data class SelectCareerStartDate(val date: LocalDate): CareerBottomSheetEvent
    data class SelectCareerEndDate(val date: LocalDate): CareerBottomSheetEvent

    data class CareerDetailInput(val detail: String): CareerBottomSheetEvent

    data class AddCareerItem(val data: CareerFormData): CareerBottomSheetEvent

    data object DeleteCareerItem: CareerBottomSheetEvent
}

sealed interface ResumeStep2Event : UiEvent {
    data class CareerSheetEvent(val event: CareerBottomSheetEvent): ResumeStep2Event
    data class DeleteCareerItem(val id: String): ResumeStep2Event
}

sealed interface ResumeStep2Effect : UiEffect {

}