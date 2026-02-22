package com.example.data.repositoryimpl

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel
import com.nonggle.model.map
import com.nonggle.network.model.resume.ResumeCreateResponseDto
import com.nonggle.network.model.resume.asExternalModel
import com.nonggle.network.model.resume.asNetworkModel
import com.nonggle.network.model.resume.asNetworkModule
import com.nonggle.network.service.ResumeService
import java.io.InputStream
import javax.inject.Inject

class ResumeRepositoryImpl @Inject constructor(
    private val resumeService: ResumeService
) : ResumeRepository {
    override suspend fun createResume(resume: ResumeWritingModel, imageInputStream: InputStream): AppResult<ResumeCreateComplete> {
        val apiResult = resumeService.createResume(resume = resume.asNetworkModel(), imageMeta = resume.imageMeta.asNetworkModule(), imageInputStream = imageInputStream)
        return apiResult.map(ResumeCreateResponseDto::asExternalModel)
    }
}