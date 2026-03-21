package com.nonggle.domain.repository
import com.nonggle.model.ResumeWritingModel

interface ResumeDraftStoreInterface {
    suspend fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel)

    suspend fun snapshot(): ResumeWritingModel
}