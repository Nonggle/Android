package com.example.domain.usecase

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.SingleResume
import javax.inject.Inject

class ResumeSingleViewUseCase @Inject constructor(
    private val resumeRepository: ResumeRepository
) {
    suspend operator fun invoke(resumeId: Long): AppResult<SingleResume> {
        return resumeRepository.getSingleResume(resumeId = resumeId)
    }
}