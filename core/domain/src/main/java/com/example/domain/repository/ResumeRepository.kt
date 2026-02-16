package com.example.domain.repository

import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel

interface ResumeRepository {
    suspend fun createResume(resume: ResumeWritingModel): AppResult<ResumeCreateComplete>
}