package com.nonggle.feature.resume_view.impl

import com.example.core.ui.BaseViewModel
import com.example.domain.usecase.ResumeSingleViewUseCase
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeViewViewModel @Inject constructor(
    private val resumeSingleViewUseCase: ResumeSingleViewUseCase
): BaseViewModel<ResumeViewEvent, ResumeViewState, ResumeViewEffect>(ResumeViewState()) {

    init {
        /// TODO: 화면 진입과 동시에 이력서 정보 로드 로직 구축 예정
    }

    override fun onEvent(event: ResumeViewEvent) {

    }
}