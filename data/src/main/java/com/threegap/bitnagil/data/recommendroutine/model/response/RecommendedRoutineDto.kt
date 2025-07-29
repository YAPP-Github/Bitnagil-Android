package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
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
    val recommendedSubRoutineSearchResult: List<RecommendedSubRoutineDto>,
)

fun RecommendedRoutineDto.toDomain(): RecommendRoutine =
    RecommendRoutine(
        id = recommendedRoutineId,
        name = recommendedRoutineName,
        description = recommendedRoutineDescription,
        level = RecommendLevel.fromString(recommendedRoutineLevel),
        executionTime = executionTime,
        recommendSubRoutines = recommendedSubRoutineSearchResult.map { it.toDomain() },
    )
