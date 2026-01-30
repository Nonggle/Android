package com.example.impl.step4

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep4ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep4Event, ResumeStep4State, ResumeStep4Effect>(initialState = ResumeStep4State()) {

    override fun onEvent(event: ResumeStep4Event) {

    }


}