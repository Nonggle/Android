package com.example.feature.resume.impl

import com.example.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeWriteViewModel @Inject constructor(

) : BaseViewModel<ResumeEvent, ResumeState, ResumeEffect>(
        initialState = ResumeState()
    ) {
    override fun onEvent(event: ResumeEvent) {
        when(event) {

            else -> {}
        }
    }
}