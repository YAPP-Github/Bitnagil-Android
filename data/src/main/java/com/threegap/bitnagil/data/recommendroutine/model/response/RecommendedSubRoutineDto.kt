package com.threegap.bitnagil.data.recommendroutine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedSubRoutineDto(
    @SerialName("recommendedSubRoutineId")
    val recommendedSubRoutineId: Int,
    @SerialName("recommendedSubRoutineName")
    val recommendedSubRoutineName: String
)
