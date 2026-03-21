package com.nonggle.resume.impl.main

import androidx.lifecycle.viewModelScope
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeMainViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface,
) : BaseViewModel<ResumeMainEvent, ResumeMainState, ResumeMainEffect>(
    initialState = ResumeMainState()
) {

    override fun onEvent(event: ResumeMainEvent) {
        when (event) {
            is ResumeMainEvent.NavigateToComplete -> {
                if(validateResume()) {
                    updateState { copy(submitStatus = true) }
                    postEffect(ResumeMainEffect.NavigateToComplete)
                }
            }
            else -> {}
        }
    }

    private fun validateResume(): Boolean {
        viewModelScope.async {
            val resumeResult = resumeStore.snapshot()
            val isNameValid = resumeResult.userName.isNotEmpty()
            val isBirthDateValid = resumeResult.birthDate.isNotEmpty()
            val isGenderValid = resumeResult.gender.isNotEmpty()
            val isIntroduceDetailValid = resumeResult.introduceDetail.isNotEmpty()
            val isPersonalityListValid = resumeResult.personalityList.isNotEmpty()
            val isIntroduceValid = resumeResult.introduce.isNotEmpty()
            if (isNameValid && isBirthDateValid && isGenderValid && isIntroduceValid && isIntroduceDetailValid && isPersonalityListValid
            ) {
                return@async true
            }
        }
        return false
    }
}