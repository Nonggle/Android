package com.nonggle.network.service

import com.nonggle.common.network.IoDispatcher
import com.nonggle.model.AppResult
import com.nonggle.network.di.ApiClient
import com.nonggle.network.model.resume.ImageMeta
import com.nonggle.network.model.resume.ResumeCreateRequestDto
import com.nonggle.network.model.resume.ResumeCreateResponseDto
import com.nonggle.network.model.resume.ResumeDto
import com.nonggle.network.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import java.io.InputStream
import javax.inject.Inject

interface ResumeService {
    suspend fun createResume(
        resume: ResumeCreateRequestDto,
        imageMeta: ImageMeta,
        imageInputStream: () -> InputStream
    ): AppResult<ResumeCreateResponseDto>

    suspend fun getSingleResume(resumeId: Long): AppResult<ResumeDto>

    suspend fun getAllResumes(): AppResult<List<ResumeDto>>
}

class ResumeServiceImpl @Inject constructor(
    @ApiClient private val baseClient: HttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ResumeService {

    override suspend fun createResume(
        resume: ResumeCreateRequestDto,
        imageMeta: ImageMeta,
        imageInputStream: () -> InputStream
    ): AppResult<ResumeCreateResponseDto> {
        return safeApiCall(ioDispatcher) {
            val jsonString = Json.encodeToString(ResumeCreateRequestDto.serializer(), resume)

            baseClient.post("/api/v1/resumes") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            appendInput(
                                key = "file",
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, imageMeta.mimeType)
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=\"${imageMeta.name}\""
                                    )
                                },
                            ) {
                                imageInputStream().asInput()
                            }

                            append(
                                key = "data",
                                value = jsonString,
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        ContentType.Application.Json.toString()
                                    )
                                }
                            )
                        }
                    )
                )
                onUpload { bytesSentTotal, contentLength -> println("Uploaded $bytesSentTotal bytes from $contentLength") }
            }
        }
    }

    override suspend fun getSingleResume(resumeId: Long): AppResult<ResumeDto> {
        return safeApiCall(ioDispatcher) {
            baseClient.get("/api/v1/resumes/${resumeId}")
        }
    }

    override suspend fun getAllResumes(): AppResult<List<ResumeDto>> {
        return safeApiCall(ioDispatcher) {
            baseClient.get("/api/v1/resumes")
        }
    }

}