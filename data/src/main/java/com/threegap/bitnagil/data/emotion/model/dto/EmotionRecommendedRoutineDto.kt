package com.threegap.bitnagil.data.emotion.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmotionRecommendedRoutineDto(
    @SerialName("recommendedRoutineId")
    val recommendedRoutineId: Int,
    @SerialName("recommendedRoutineName")
    val recommendedRoutineName: String,
    @SerialName("routineDescription")
    val routineDescription: String,
    @SerialName("recommendedSubRoutines")
    val recommendedSubRoutines: List<EmotionRecommendedSubRoutineDto>,
)
