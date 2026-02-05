package com.example.feature.resume.impl.step2

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep2ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep2Event, ResumeStep2State, ResumeStep2Effect>(initialState = ResumeStep2State()) {

    override fun onEvent(event: ResumeStep2Event) {
        when (event) {
            is ResumeStep2Event.CareerSheetEvent -> handleCareerSheetEvent(event.event)
            is ResumeStep2Event.DeleteCareerItem -> updateState { copy(careerList = this.careerList.filter { it.id != event.id }) }
        }
    }

    private fun handleCareerSheetEvent(careerSheetEvent: CareerBottomSheetEvent) {
        when (careerSheetEvent) {
            // 근무 시작일 선택
            is CareerBottomSheetEvent.SelectCareerStartDate -> {
                updateState { copy(careerFormData = this.careerFormData.copy(careerStartDate = careerSheetEvent.date)) }
            }

            // 근무 종료일 선택 (1개월 이상)
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
            is CareerBottomSheetEvent.AddCareerItem -> {updateState { copy(careerList = this.careerList + careerSheetEvent.data) }}
        }
    }

    private fun getDiffYearMonth() {

    }
}