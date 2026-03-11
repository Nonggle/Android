package com.nonggle.domain.usecase

import com.nonggle.domain.repository.ImageContentReaderRepository
import javax.inject.Inject

class ImageContentReadUseCase @Inject constructor(
    private val repository: ImageContentReaderRepository
) {
    suspend operator fun invoke(contentUri: String) = repository.getImageMeta(contentUri)
}