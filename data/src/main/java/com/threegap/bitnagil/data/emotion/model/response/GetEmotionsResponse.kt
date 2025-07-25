package com.threegap.bitnagil.data.emotion.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEmotionsResponse(
    @SerialName("emotionMarbleTypes")
    val emotionMarbleTypes: List<String>
)
