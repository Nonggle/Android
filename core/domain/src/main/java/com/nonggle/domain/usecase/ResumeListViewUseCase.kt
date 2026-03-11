package com.nonggle.domain.usecase

import com.nonggle.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.SingleResume
import javax.inject.Inject

class ResumeListViewUseCase @Inject constructor(
    private val resumeRepository: ResumeRepository
) {
    suspend operator fun invoke(): AppResult<List<SingleResume>> {
        return resumeRepository.getAllResume()
    }
}