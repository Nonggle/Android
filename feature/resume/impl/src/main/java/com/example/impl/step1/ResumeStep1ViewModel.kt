package com.example.feature.resume.impl.step1

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep1ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep1Event, ResumeStep1State, ResumeStep1Effect>(initialState = ResumeStep1State()) {
    override fun onEvent(event: ResumeStep1Event) {

    }


}