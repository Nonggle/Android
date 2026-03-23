package com.nonggle.data.repositoryimpl

import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumeDraftStore  @Inject constructor(): ResumeDraftStoreInterface {
    private val _draft = MutableStateFlow(ResumeWritingModel())
    override val draft: StateFlow<ResumeWritingModel> = _draft

    override suspend fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel) {
        _draft.update(reducer)
    }

    override suspend fun snapshot(): ResumeWritingModel = _draft.value

}
