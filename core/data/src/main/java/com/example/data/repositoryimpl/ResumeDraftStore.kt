package com.example.data.repositoryimpl

import com.example.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumeDraftStore  @Inject constructor(): ResumeDraftStoreInterface {
    private val _draft = MutableStateFlow(ResumeWritingModel())
    val draft: StateFlow<ResumeWritingModel> = _draft

    override fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel) {
        _draft.update(reducer)
    }

    override fun snapshot(): ResumeWritingModel = _draft.value

}