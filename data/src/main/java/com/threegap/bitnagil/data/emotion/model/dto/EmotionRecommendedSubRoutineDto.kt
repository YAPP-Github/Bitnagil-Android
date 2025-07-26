package com.threegap.bitnagil.data.emotion.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmotionRecommendedSubRoutineDto(
    @SerialName("recommendedSubRoutineId")
    val recommendedSubRoutineId: Int,
    @SerialName("recommendedSubRoutineName")
    val recommendedSubRoutineName: String,
)
