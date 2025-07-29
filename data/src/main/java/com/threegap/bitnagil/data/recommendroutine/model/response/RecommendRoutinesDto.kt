package com.threegap.bitnagil.data.recommendroutine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendRoutinesDto(
    @SerialName("recommendedRoutines")
    val recommendedRoutines: Map<String, List<RecommendedRoutineDto>>,
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String?
)
