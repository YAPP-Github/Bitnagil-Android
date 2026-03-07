package com.threegap.bitnagil.data.emotion.model.response

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DailyEmotionResponse(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: EmotionMarbleType?,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("emotionMarbleHomeMessage")
    val emotionMarbleHomeMessage: String?,
)

fun DailyEmotionResponse.toDomain(): DailyEmotion =
    DailyEmotion(
        type = emotionMarbleType,
        name = emotionMarbleName,
        imageUrl = imageUrl,
        homeMessage = emotionMarbleHomeMessage,
        fetchedDate = LocalDate.now(),
    )
