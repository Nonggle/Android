package com.example.impl

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeWriteViewModel @Inject constructor() :
    BaseViewModel<ResumeEvent, ResumeState, ResumeEffect>(
        initialState = ResumeState()
    ) {
    override fun onEvent(event: ResumeEvent) {
        when(event) {

            else -> {}
        }
    }
}
