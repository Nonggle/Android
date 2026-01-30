package com.example.impl.main

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeMainViewModel @Inject constructor(

) : BaseViewModel<ResumeMainEvent, ResumeMainState, ResumeMainEffect>(
        initialState = ResumeMainState()
    ) {

    override fun onEvent(event: ResumeMainEvent) {
        when(event) {

            else -> {}
        }
    }
}