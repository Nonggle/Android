package com.nonggle.resume.impl.main

import androidx.lifecycle.viewModelScope
import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeMainViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface,
) : BaseViewModel<ResumeMainEvent, ResumeMainState, ResumeMainEffect>(
    initialState = ResumeMainState()
) {
    init {
        viewModelScope.launch {
            resumeStore.draft.collectLatest { resume ->
                updateState { copy(submitStatus = validateResume(resume)) }
            }
        }
    }

    override fun onEvent(event: ResumeMainEvent) {
        when (event) {
            is ResumeMainEvent.NavigateToComplete -> {
                if (currentState.submitStatus) {
                    postEffect(ResumeMainEffect.NavigateToComplete)
                }
            }
        }
    }

    private fun validateResume(resume: ResumeWritingModel): Boolean {
        val isNameValid = resume.userName.isNotEmpty()
        val isBirthDateValid = resume.birthDate.isNotEmpty()
        val isGenderValid = resume.gender.isNotEmpty()
        val isIntroduceDetailValid = resume.introduceDetail.isNotEmpty()
        val isPersonalityListValid = resume.personalityList.isNotEmpty()
        val isIntroduceValid = resume.introduce.isNotEmpty()

        return isNameValid &&
            isBirthDateValid &&
            isGenderValid &&
            isIntroduceValid &&
            isIntroduceDetailValid &&
            isPersonalityListValid
    }
}
