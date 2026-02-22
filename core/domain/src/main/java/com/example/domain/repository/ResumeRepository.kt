package com.example.domain.repository

import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel
import com.nonggle.model.SingleResume
import java.io.InputStream

interface ResumeRepository {
    suspend fun createResume(resume: ResumeWritingModel, imageInputStream: () -> InputStream): AppResult<ResumeCreateComplete>

    suspend fun getAllResume(): AppResult<List<SingleResume>>

    suspend fun getSingleResume(resumeId: Long): AppResult<SingleResume>
}
