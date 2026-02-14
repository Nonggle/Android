package com.example.impl.complete_resume

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompleteResumeViewModel @Inject constructor(): BaseViewModel<CompleteResumeEvent, CompleteResumeState, CompleteResumeEffect>(
    CompleteResumeState()
) {
    init {
        /// TODO: 이력서 서버 업로드 로직 추가 후 상태 업데이트
    }
    override fun onEvent(event: CompleteResumeEvent) {

    }
}