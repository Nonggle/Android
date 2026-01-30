package com.example.feature.resume.impl.step2

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep2ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep2Event, ResumeStep2State, ResumeStep2Effect>(initialState = ResumeStep2State()) {

    override fun onEvent(event: ResumeStep2Event) {

    }


}