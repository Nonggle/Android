package com.example.feature.resume.impl.step3

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep3Event, ResumeStep3State, ResumeStep3Effect>(initialState = ResumeStep3State()) {

    override fun onEvent(event: ResumeStep3Event) {

    }


}