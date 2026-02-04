package com.example.feature.resume.impl.step3

import com.example.core.ui.BaseViewModel
import com.example.feature.resume.impl.step1.CertificationTag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep3Event, ResumeStep3State, ResumeStep3Effect>(initialState = ResumeStep3State()) {

    override fun onEvent(event: ResumeStep3Event) {
        when(event) {
            is ResumeStep3Event.IntroduceChanged -> updateState { copy(introduce = event.introduce) }
            is ResumeStep3Event.PersonalityInput -> updateState { copy(personality = event.value) }
            is ResumeStep3Event.RemovePersonalityChip -> updateState { copy(personalityList = personalityList.filter { it.id != event.id }) }
            is ResumeStep3Event.AddPersonalityChip -> addCertification()
            is ResumeStep3Event.IntroduceDetailInput -> updateState { copy(introduceDetail = event.value) }
        }
    }

    private fun addCertification() {
        updateState {
            if ((personality ?: "").trim().isEmpty()) return@updateState this
            val certificationTag = PersonalityTag(
                personality = personality?.trim() ?: ""
            )

            copy(
                personalityList = personalityList + certificationTag,
                personality = ""
            )
        }
    }


}