package com.threegap.bitnagil.data.activitylog.model.response

import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EmotionMarbleResponse(
    @SerialName("date")
    val date: String,
    @SerialName("emotionMarbleType")
    val emotionMarbleType: EmotionMarbleType,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String,
    @SerialName("imageUrl")
    val imageUrl: String,
)

fun EmotionMarbleResponse.toDomain(): EmotionMarble =
    EmotionMarble(
        date = LocalDate.parse(date),
        type = emotionMarbleType,
        name = emotionMarbleName,
        imageUrl = imageUrl,
    )
