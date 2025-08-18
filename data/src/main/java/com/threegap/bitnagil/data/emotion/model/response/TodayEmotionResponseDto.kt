package com.threegap.bitnagil.data.emotion.model.response

import com.threegap.bitnagil.domain.emotion.model.TodayEmotion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayEmotionResponseDto(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String?,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("emotionMarbleHomeMessage")
    val emotionMarbleHomeMessage: String?,
)

fun TodayEmotionResponseDto.toDomain(): TodayEmotion? {
    return if (emotionMarbleType != null && emotionMarbleName != null && imageUrl != null && emotionMarbleHomeMessage != null) {
        TodayEmotion(
            type = emotionMarbleType,
            name = emotionMarbleName,
            imageUrl = imageUrl,
            homeMessage = emotionMarbleHomeMessage,
        )
    } else {
        null
    }
}
