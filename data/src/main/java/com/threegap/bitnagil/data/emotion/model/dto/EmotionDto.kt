package com.threegap.bitnagil.data.emotion.model.dto

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
)
