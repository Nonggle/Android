package com.nonggle.resume.impl.main

import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeMainViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface,
) : BaseViewModel<ResumeMainEvent, ResumeMainState, ResumeMainEffect>(
        initialState = ResumeMainState()
    ) {

    override fun onEvent(event: ResumeMainEvent) {
        when(event) {

            else -> {}
        }
    }
}