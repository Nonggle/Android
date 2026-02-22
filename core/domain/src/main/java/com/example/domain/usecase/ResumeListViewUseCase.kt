package com.example.domain.usecase

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeListModel
import javax.inject.Inject

class ResumeListViewUseCase @Inject constructor(
    private val resumeRepository: ResumeRepository
) {
    suspend operator fun invoke(): AppResult<ResumeListModel> {
        return resumeRepository.getAllResume()
    }
}