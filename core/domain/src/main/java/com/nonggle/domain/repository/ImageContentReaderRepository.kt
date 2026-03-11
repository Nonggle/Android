package com.nonggle.domain.repository

import com.nonggle.model.ResumeWritingModel
import java.io.InputStream

interface ImageContentReaderRepository {
    suspend fun getImageMeta(contentUri: String): ResumeWritingModel.ResumeImageMeta?
    suspend fun openStream(contentUri: String): () -> InputStream
}