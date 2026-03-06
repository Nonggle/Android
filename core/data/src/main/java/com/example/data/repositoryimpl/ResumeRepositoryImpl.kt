package com.example.data.repositoryimpl

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel
import com.nonggle.model.SingleResume
import com.nonggle.model.mapNotNull
import com.nonggle.network.model.resume.ResumeCreateResponseDto
import com.nonggle.network.model.resume.ResumeDto
import com.nonggle.network.model.resume.asExternalModel
import com.nonggle.network.model.resume.asNetworkModel
import com.nonggle.network.model.resume.asNetworkModule
import com.nonggle.network.service.ResumeService
import java.io.InputStream
import javax.inject.Inject

class ResumeRepositoryImpl @Inject constructor(
    private val resumeService: ResumeService
) : ResumeRepository {
    override suspend fun createResume(resume: ResumeWritingModel, imageInputStream: () -> InputStream): AppResult<ResumeCreateComplete> {
        val response = resumeService.createResume(resume = resume.asNetworkModel(), imageMeta = resume.imageMeta.asNetworkModule(), imageInputStream = imageInputStream)
        return response.mapNotNull(ResumeCreateResponseDto::asExternalModel)
    }

    override suspend fun getAllResume(): AppResult<List<SingleResume>> {
        val response = resumeService.getAllResumes()
        return response.mapNotNull{resumeDtoList ->
            resumeDtoList.map(ResumeDto::asExternalModel)
        }
    }

    override suspend fun getSingleResume(resumeId: Long): AppResult<SingleResume> {
        val response = resumeService.getSingleResume(resumeId = resumeId)
        return response.mapNotNull(ResumeDto::asExternalModel)
    }

    override suspend fun deleteResume(resumeId: Long): AppResult<Unit> {
        val response = resumeService.deleteResume(resumeId = resumeId)
        return when(response) {
            is AppResult.Success -> AppResult.Success(Unit)
            is AppResult.Error -> response
        }
    }


}