package com.example.data.repositoryimpl

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel
import com.nonggle.model.map
import com.nonggle.network.model.resume.ResumeCreateResponseDto
import com.nonggle.network.model.resume.asExternalModel
import com.nonggle.network.service.ResumeService
import javax.inject.Inject

class ResumeRepositoryImpl @Inject constructor(
    private val resumeService: ResumeService
): ResumeRepository {
    val tmpResume: ResumeWritingModel? = null
    fun storeStep1Data() {
        tmpResume
    }
    override suspend fun createResume(resume: ResumeWritingModel): AppResult<ResumeCreateComplete> {
        val apiResult = resumeService.createResume(resume = resume.asExternalModel())
        return apiResult.map(ResumeCreateResponseDto::asExternalModel)
    }
}