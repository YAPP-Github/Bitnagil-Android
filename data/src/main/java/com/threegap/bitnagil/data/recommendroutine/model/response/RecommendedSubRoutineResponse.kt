package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedSubRoutineResponse(
    @SerialName("recommendedSubRoutineId")
    val recommendedSubRoutineId: Long,
    @SerialName("recommendedSubRoutineName")
    val recommendedSubRoutineName: String,
)

fun RecommendedSubRoutineResponse.toDomain(): RecommendSubRoutine =
    RecommendSubRoutine(
        id = recommendedSubRoutineId,
        name = recommendedSubRoutineName,
    )
