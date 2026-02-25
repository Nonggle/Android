package com.example.domain.repository
import com.nonggle.model.ResumeWritingModel

interface ResumeDraftStoreInterface {
    fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel)

    fun snapshot(): ResumeWritingModel
}