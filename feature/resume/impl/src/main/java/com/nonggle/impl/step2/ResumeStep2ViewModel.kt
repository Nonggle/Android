package com.nonggle.resume.impl.step2

import com.nonggle.common.utils.getPeriodFormatter
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class ResumeStep2ViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface
) : BaseViewModel<ResumeStep2Event, ResumeStep2State, ResumeStep2Effect>(initialState = ResumeStep2State()) {

    override fun onEvent(event: ResumeStep2Event) {
        when (event) {
            is ResumeStep2Event.CareerSheetEvent -> handleCareerSheetEvent(event.event)
            is ResumeStep2Event.DeleteCareerItem -> deleteCareerItem(event.id)
        }
    }

    private fun handleCareerSheetEvent(careerSheetEvent: CareerBottomSheetEvent) {
        when (careerSheetEvent) {
            // 근무 시작일 선택
            is CareerBottomSheetEvent.SelectCareerStartDate -> {
                updateState { copy(careerFormData = this.careerFormData.copy(careerStartDate = careerSheetEvent.date)) }
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

    private fun sumCareerPeriod(items: List<CareerFormData>): Period =
        items.fold(Period.ZERO) { acc, item ->
            acc.plus(Period.between(item.careerStartDate, item.careerEndDate))
        }

    private fun addCareerItem(data: CareerFormData) {
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

    private fun deleteCareerItem(id: String) {
        val newCareerList = currentState.careerList.filter { it.id != id }
        updateState {
            copy(
                totalCareer = sumCareerPeriod(newCareerList),
                careerList = newCareerList
            )
        }
        deleteTempResume(newCareerList)
    }

    private fun saveTempResume(newCareerList: List<CareerFormData>) {
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
                totalCareer = getPeriodFormatter(newCareerList.map {
                    Period.between(it.careerStartDate, it.careerEndDate)
                }.reduce { acc, period -> acc.plus(period) })
            )
        }
    }

    private fun deleteTempResume(newCareerList: List<CareerFormData>) {
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
                totalCareer = getPeriodFormatter(newCareerList.map {
                    Period.between(it.careerStartDate, it.careerEndDate)
                }.reduce { acc, period -> acc.plus(period) })
            )
        }
    }
}