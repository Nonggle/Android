package com.nonggle.resume.impl.step3

import com.nonggle.ui.BaseViewModel
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor(
    private val resumeStore: ResumeDraftStoreInterface
) : BaseViewModel<ResumeStep3Event, ResumeStep3State, ResumeStep3Effect>(initialState = ResumeStep3State()) {

    override fun onEvent(event: ResumeStep3Event) {
        when(event) {
            is ResumeStep3Event.IntroduceChanged -> introduceChanged(event.introduce)
            is ResumeStep3Event.PersonalityInput -> updateState { copy(personality = event.value) }
            is ResumeStep3Event.RemovePersonalityChip -> removePersonalityChip(event.id)
            is ResumeStep3Event.AddPersonalityChip -> addPersonality()
            is ResumeStep3Event.IntroduceDetailInput -> introduceDetailChanged(event.value)
        }
    }

    private fun introduceChanged(introduce: String) {
        updateState { copy(introduce = introduce) }
        resumeStore.update {
            it.copy(introduce = introduce)
        }
    }

    private fun removePersonalityChip(chipId: String) {
        val newList = currentState.personalityList.filter { it.id != chipId }
        updateState { copy(personalityList = newList) }
        resumeStore.update {
            it.copy(personalityList = newList.map { it.personality })
        }
    }

    private fun addPersonality() {
        val newList = currentState.personalityList + PersonalityTag(personality = currentState.personality?.trim() ?: "")
        updateState {
            if ((personality ?: "").trim().isEmpty()) return@updateState this
            copy(
                personalityList = newList,
                personality = ""
            )
        }
        resumeStore.update {
            it.copy(personalityList = newList.map { it.personality })
        }
    }

    private fun introduceDetailChanged(value: String) {
        updateState { copy(introduceDetail = value) }
        resumeStore.update {
            it.copy(introduceDetail = value)
        }
    }
}