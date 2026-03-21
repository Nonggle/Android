package com.nonggle.domain.repository
import com.nonggle.model.ResumeWritingModel
import kotlinx.coroutines.flow.StateFlow

interface ResumeDraftStoreInterface {
    val draft: StateFlow<ResumeWritingModel>

    suspend fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel)

    suspend fun snapshot(): ResumeWritingModel
}
