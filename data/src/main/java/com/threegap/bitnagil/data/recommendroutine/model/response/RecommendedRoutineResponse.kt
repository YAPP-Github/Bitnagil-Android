package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedRoutineResponse(
    @SerialName("recommendedRoutineId")
    val recommendedRoutineId: Long,
    @SerialName("recommendedRoutineName")
    val recommendedRoutineName: String,
    @SerialName("recommendedRoutineDescription")
    val recommendedRoutineDescription: String,
    @SerialName("recommendedRoutineLevel")
    val recommendedRoutineLevel: RecommendLevel,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("recommendedRoutineType")
    val recommendedRoutineType: RecommendCategory,
    @SerialName("recommendedSubRoutineSearchResult")
    val recommendedSubRoutineSearchResult: List<RecommendedSubRoutineResponse>,
)

fun RecommendedRoutineResponse.toDomain(): RecommendRoutine =
    RecommendRoutine(
        id = this.recommendedRoutineId,
        name = this.recommendedRoutineName,
        description = this.recommendedRoutineDescription,
        level = this.recommendedRoutineLevel,
        executionTime = this.executionTime,
        category = this.recommendedRoutineType,
        subRoutines = this.recommendedSubRoutineSearchResult.map { it.toDomain() },
    )
