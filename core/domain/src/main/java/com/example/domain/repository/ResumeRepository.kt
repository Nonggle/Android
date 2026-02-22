package com.example.domain.repository

import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeListModel
import com.nonggle.model.ResumeWritingModel
import java.io.InputStream

interface ResumeRepository {
    suspend fun createResume(resume: ResumeWritingModel, imageInputStream: () -> InputStream): AppResult<ResumeCreateComplete>

    suspend fun getAllResume(): AppResult<ResumeListModel>
}