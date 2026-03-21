package com.nonggle.domain.usecase

import com.nonggle.domain.repository.ImageContentReaderRepository
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.domain.repository.ResumeRepository
import com.nonggle.model.AppResult
import com.nonggle.model.ResumeCreateComplete
import javax.inject.Inject

class ResumeCreateUseCase @Inject constructor(
    private val resumeRepository: ResumeRepository,
    private val imageContentReaderRepository: ImageContentReaderRepository,
    private val resumeStore: ResumeDraftStoreInterface,

    ) {
    suspend operator fun invoke(): AppResult<ResumeCreateComplete> {
        val resumeTempData = resumeStore.snapshot()
        val inputStream = imageContentReaderRepository.openStream(resumeTempData.imageMeta.uri)
        return resumeRepository.createResume(resumeTempData, inputStream)
    }
}