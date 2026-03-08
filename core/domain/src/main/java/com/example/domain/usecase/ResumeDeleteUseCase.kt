package com.example.domain.usecase

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import javax.inject.Inject

class ResumeDeleteUseCase @Inject constructor(
    private val resumeRepository: ResumeRepository
) {
    suspend operator fun invoke(resumeId: Long): AppResult<Unit> {
        return resumeRepository.deleteResume(resumeId = resumeId)
    }
}