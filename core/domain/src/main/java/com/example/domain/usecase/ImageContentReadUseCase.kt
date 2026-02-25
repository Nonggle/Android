package com.example.domain.usecase

import com.example.domain.repository.ImageContentReaderRepository
import javax.inject.Inject

class ImageContentReadUseCase @Inject constructor(
    private val repository: ImageContentReaderRepository
) {
    suspend operator fun invoke(contentUri: String) = repository.getImageMeta(contentUri)
}