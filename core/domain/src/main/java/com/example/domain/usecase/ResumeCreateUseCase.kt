package com.example.domain.usecase

import com.example.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import com.nonggle.model.ResumeWritingModel
import javax.inject.Inject

class ResumeCreateUseCase @Inject constructor(
    private val repository: ResumeRepository
) {
    suspend operator fun invoke(resume: ResumeWritingModel): AppResult<ResumeCreateComplete> {
        return repository.createResume(resume)
    }
}