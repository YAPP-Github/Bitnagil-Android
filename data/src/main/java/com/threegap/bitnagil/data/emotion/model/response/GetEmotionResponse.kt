package com.threegap.bitnagil.data.emotion.model.response

import com.threegap.bitnagil.domain.emotion.model.Emotion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEmotionResponse(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String?,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
)

fun GetEmotionResponse.toDomain(): Emotion? {
    return if (emotionMarbleType != null && emotionMarbleName != null && imageUrl != null) {
        Emotion(
            emotionType = emotionMarbleType,
            emotionMarbleName = emotionMarbleName,
            imageUrl = imageUrl,
        )
    } else {
        null
    }
}
