package com.nonggle.feature.resume_view.impl

import com.example.core.ui.BaseViewModel
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeViewViewModel @Inject constructor(

): BaseViewModel<ResumeViewEvent, ResumeViewState, ResumeViewEffect>(ResumeViewState()) {
    override fun onEvent(event: ResumeViewEvent) {

    }
}