package com.threegap.bitnagil.data.emotion.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterEmotionRequest(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String
)
