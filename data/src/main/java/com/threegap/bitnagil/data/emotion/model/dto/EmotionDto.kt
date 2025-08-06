package com.threegap.bitnagil.data.emotion.model.dto

import com.threegap.bitnagil.domain.emotion.model.Emotion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmotionDto(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String,
    @SerialName("imageUrl")
    val imageUrl: String,
) {
    fun toDomain(): Emotion =
        Emotion(
            emotionType = emotionMarbleType,
            emotionMarbleName = emotionMarbleName,
            imageUrl = imageUrl,
        )
}
