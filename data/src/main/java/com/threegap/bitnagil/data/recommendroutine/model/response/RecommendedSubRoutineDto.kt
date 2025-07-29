package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedSubRoutineDto(
    @SerialName("recommendedSubRoutineId")
    val recommendedSubRoutineId: Int,
    @SerialName("recommendedSubRoutineName")
    val recommendedSubRoutineName: String,
)

fun RecommendedSubRoutineDto.toDomain(): RecommendSubRoutine =
    RecommendSubRoutine(
        id = recommendedSubRoutineId,
        name = recommendedSubRoutineName,
    )
