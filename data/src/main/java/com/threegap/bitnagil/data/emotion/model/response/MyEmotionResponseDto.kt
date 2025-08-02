package com.threegap.bitnagil.data.emotion.model.response

import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.MyEmotion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyEmotionResponseDto(
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String?,
    @SerialName("emotionMarbleName")
    val emotionMarbleName: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
)

fun MyEmotionResponseDto.toDomain(): MyEmotion =
    MyEmotion(
        emotionMarbleType = emotionMarbleType?.let { Emotion.valueOf(it) },
        emotionMarbleName = emotionMarbleName,
        imageUrl = imageUrl,
    )
