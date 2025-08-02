package com.threegap.bitnagil.data.emotion.model.response

import com.threegap.bitnagil.data.emotion.model.dto.EmotionRecommendedRoutineDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterEmotionResponse(
    @SerialName("recommendedRoutines")
    val recommendedRoutines: List<EmotionRecommendedRoutineDto>,
)
