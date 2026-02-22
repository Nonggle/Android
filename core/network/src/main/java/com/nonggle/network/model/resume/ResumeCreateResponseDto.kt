package com.nonggle.network.model.resume

import com.nonggle.model.ResumeCreateComplete
import kotlinx.serialization.Serializable

@Serializable
data class ResumeCreateResponseDto(
    val id: Long
)

fun ResumeCreateResponseDto.asExternalModel(): ResumeCreateComplete =
    ResumeCreateComplete(
        id = id
    )