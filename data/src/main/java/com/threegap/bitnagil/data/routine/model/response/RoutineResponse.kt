package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineResponse(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<DayOfWeek>,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("routineDate")
    val routineDate: String,
    @SerialName("routineCompleteYn")
    val routineCompleteYn: Boolean,
    @SerialName("subRoutineNames")
    val subRoutineNames: List<String>,
    @SerialName("subRoutineCompleteYn")
    val subRoutineCompleteYn: List<Boolean>,
    @SerialName("recommendedRoutineType")
    val recommendedRoutineType: RecommendCategory?,
    @SerialName("routineDeletedYn")
    val routineDeletedYn: Boolean,
    @SerialName("routineStartDate")
    val routineStartDate: String,
    @SerialName("routineEndDate")
    val routineEndDate: String,
)

fun RoutineResponse.toDomain(): Routine =
    Routine(
        id = this.routineId,
        name = this.routineName,
        repeatDays = this.repeatDay,
        executionTime = this.executionTime,
        routineDate = this.routineDate,
        isCompleted = this.routineCompleteYn,
        subRoutineNames = this.subRoutineNames,
        subRoutineCompletionStates = this.subRoutineCompleteYn,
        recommendedRoutineType = this.recommendedRoutineType,
        isDeleted = routineDeletedYn,
        startDate = this.routineStartDate,
        endDate = this.routineEndDate,
    )
