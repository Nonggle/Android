package com.nonggle.resume.impl.step2

import androidx.lifecycle.viewModelScope
import com.nonggle.common.utils.getPeriodFormatter
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import com.nonggle.resume.impl.step1.ResumeStep1Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class ResumeStep2ViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface
) : BaseViewModel<ResumeStep2Event, ResumeStep2State, ResumeStep2Effect>(initialState = ResumeStep2State()) {

    override fun onEvent(event: ResumeStep2Event) {
        viewModelScope.launch {
            when (event) {
                is ResumeStep2Event.CareerSheetEvent -> handleCareerSheetEvent(event.event)
                is ResumeStep2Event.DeleteCareerItem -> deleteCareerItem(event.id)
            }
        }
    }

    private suspend fun handleCareerSheetEvent(careerSheetEvent: CareerBottomSheetEvent) {
        when (careerSheetEvent) {
            // 근무 시작일 선택
            is CareerBottomSheetEvent.SelectCareerStartDate -> {
                if(validateStartDate(careerSheetEvent.date)) {
                    updateState { copy(careerFormData = this.careerFormData.copy(careerStartDate = careerSheetEvent.date)) }
                }
            }

            // 근무 종료일 선택
            is CareerBottomSheetEvent.SelectCareerEndDate -> {
                updateState { copy(careerFormData = this.careerFormData.copy(careerEndDate = careerSheetEvent.date)) }
            }

            // 작성한 이력정보 삭제
            is CareerBottomSheetEvent.DeleteCareerItem -> {
                updateState { copy(careerFormData = CareerFormData()) }
            }

            // 경력 소개 문구 작성
            is CareerBottomSheetEvent.CareerDescriptionInput -> {
                updateState { copy(careerFormData = this.careerFormData.copy(careerDescription = careerSheetEvent.description)) }
            }

            // 작업 상세 내용 작성
            is CareerBottomSheetEvent.CareerDetailInput -> {
                updateState { copy(careerFormData = this.careerFormData.copy(careerDetail = careerSheetEvent.detail)) }
            }

            // 경력 작성 완료 후 리스트 추가
            is CareerBottomSheetEvent.AddCareerItem -> addCareerItem(careerSheetEvent.data)

        }
    }

    private fun validateStartDate(date: LocalDate): Boolean {
        if (date.isAfter(LocalDate.now())) {
            postEffect(ResumeStep2Effect.SendStartCareerNotValidMessage)
            return false
        }
        return true
    }

    private fun sumCareerPeriod(items: List<CareerFormData>): Period {
        val totalMonths = items.sumOf { item ->
            val period = Period.between(item.careerStartDate, item.careerEndDate)
            period.years * 12 + period.months
        }
        val totalDays = items.sumOf { item ->
            Period.between(item.careerStartDate, item.careerEndDate).days
        }

        val normalizedMonths = totalMonths + (totalDays / 30)
        val normalizedDays = totalDays % 30

        return Period.of(
            normalizedMonths / 12,
            normalizedMonths % 12,
            normalizedDays
        )
    }

    private suspend fun addCareerItem(data: CareerFormData) {
        val newCareerList = currentState.careerList + data
        updateState {
            copy(
                careerList = this.careerList + data,
                careerFormData = CareerFormData(),
                totalCareer = sumCareerPeriod(newCareerList)
            )
        }
        saveTempResume(newCareerList)
    }

    private suspend fun deleteCareerItem(id: String) {
        val newCareerList = currentState.careerList.filter { it.id != id }
        updateState {
            copy(
                totalCareer = sumCareerPeriod(newCareerList),
                careerList = newCareerList
            )
        }
        deleteTempResume(newCareerList)
    }

    private suspend fun saveTempResume(newCareerList: List<CareerFormData>) {
        val totalCareer = sumCareerPeriod(newCareerList)
        resumeStore.update {
            it.copy(
                careerList = newCareerList.map {
                    ResumeWritingModel.Career(
                        careerStartDate = it.careerStartDate ?: LocalDate.now(),
                        careerEndDate = it.careerEndDate ?: LocalDate.now(),
                        careerPeriod = getPeriodFormatter(
                            period = Period.between(
                                it.careerStartDate,
                                it.careerEndDate
                            )
                        ),
                        careerDescription = it.careerDescription,
                        careerDetail = it.careerDetail
                    )
                },
                totalCareer = getPeriodFormatter(totalCareer)
            )
        }
    }

    private suspend fun deleteTempResume(newCareerList: List<CareerFormData>) {
        val totalCareer = sumCareerPeriod(newCareerList)
        resumeStore.update {
            it.copy(
                careerList = newCareerList.map {
                    ResumeWritingModel.Career(
                        it.careerStartDate ?: LocalDate.now(),
                        it.careerEndDate ?: LocalDate.now(),
                        careerPeriod = getPeriodFormatter(
                            period = Period.between(
                                it.careerStartDate,
                                it.careerEndDate
                            )
                        ),
                        it.careerDescription,
                        it.careerDetail
                    )
                },
                totalCareer = getPeriodFormatter(totalCareer)
            )
        }
    }
}
