package com.threegap.bitnagil.data.recommendroutine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedRoutineDto(
    @SerialName("recommendedRoutineId")
    val recommendedRoutineId: Int,
    @SerialName("recommendedRoutineName")
    val recommendedRoutineName: String,
    @SerialName("recommendedRoutineDescription")
    val recommendedRoutineDescription: String,
    @SerialName("recommendedRoutineLevel")
    val recommendedRoutineLevel: String,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("recommendedSubRoutineSearchResult")
    val recommendedSubRoutineSearchResult: List<RecommendedSubRoutineDto>
)
