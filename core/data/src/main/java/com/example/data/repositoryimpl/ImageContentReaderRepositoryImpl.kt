package com.example.data.repositoryimpl

import android.content.Context
import android.provider.MediaStore
import com.example.domain.repository.ImageContentReaderRepository
import com.nonggle.model.ResumeWritingModel
import java.io.InputStream
import javax.inject.Inject
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext

class ImageContentReaderRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ImageContentReaderRepository {
    override suspend fun getImageMeta(contentUri: String): ResumeWritingModel.ResumeImageMeta? {
        val uri = contentUri.toUri()
        val cr = context.contentResolver

        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.MIME_TYPE
        )
        val cursor = cr.query(uri, projection, null, null, null)

        return cursor?.use { c ->
            if (!c.moveToFirst()) error("No rows for uri=$contentUri")

            val nameIdx = c.getColumnIndexOrThrow(projection[0])
            val sizeIdx = c.getColumnIndexOrThrow(projection[1])
            val typeIdx = c.getColumnIndexOrThrow(projection[2])

            val name = c.getString(nameIdx) ?: "image_${System.currentTimeMillis()}.jpg"
            val size = c.getLong(sizeIdx)
            val type = c.getString(typeIdx) ?: "image/jpeg"

            ResumeWritingModel.ResumeImageMeta(
                uri = contentUri,
                name = name,
                size = size,
                mimeType = type
            )
        }
    }

    override suspend fun openStream(contentUri: String): () -> InputStream {
        val uri = contentUri.toUri()
        return {
            context.contentResolver.openInputStream(uri) ?: error("Failed to open uri=$contentUri")
        }
    }
}
