package com.nonggle.network.model.resume


import com.nonggle.model.ResumeWritingModel
import kotlinx.serialization.Serializable

@Serializable
data class ResumeCreateRequestDto(
    val userName: String,
    val birthDate: String,
    val introduction: String,
    val gender: String,
    val certificationList: List<CertificationTag>?,
    val careerList: List<CareerFormData>,
    val totalCareer: String,
    val introduce: String,
    val introduceDetail: String,
    val personalityList: List<PersonalityTag>,
) {
    @Serializable
    data class CertificationTag(
        val certificationTitle: String,
    )

    @Serializable
    data class CareerFormData(
        val careerStartDate: String,
        val careerEndDate: String,
        val careerDescription: String,
        val careerDetail: String
    )

    @Serializable
    data class PersonalityTag(
        val personality: String,
    )
}

data class ImageMeta(
    val uri: String,
    val name: String,
    val size: Long,
    val mimeType: String
)

fun ResumeWritingModel.asNetworkModel(): ResumeCreateRequestDto =
    ResumeCreateRequestDto(
        userName = userName,
        birthDate = birthDate,
        introduction = introduction,
        gender = gender,
        certificationList = certificationList?.map { ResumeCreateRequestDto.CertificationTag(it) } ?: emptyList(),
        careerList = careerList.map {
            ResumeCreateRequestDto.CareerFormData(
                careerStartDate = it.careerStartDate.toString(),
                careerEndDate = it.careerEndDate.toString(),
                careerDescription = it.careerDescription,
                careerDetail = it.careerDetail
            )
        },
        totalCareer = totalCareer,
        introduceDetail = introduceDetail,
        personalityList = personalityList.map { ResumeCreateRequestDto.PersonalityTag(it) },
        introduce  = introduce

    )

fun ResumeWritingModel.ResumeImageMeta.asNetworkModule(): ImageMeta =
    ImageMeta(
        uri = uri,
        name = name,
        size = size,
        mimeType = mimeType
    )
