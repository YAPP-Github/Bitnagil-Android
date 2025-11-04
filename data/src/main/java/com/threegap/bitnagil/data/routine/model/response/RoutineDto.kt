package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RecommendedRoutineType
import com.threegap.bitnagil.domain.routine.model.Routine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineDto(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<String>,
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
    val recommendedRoutineType: String?,
    @SerialName("routineDeletedYn")
    val routineDeletedYn: Boolean,
    @SerialName("routineStartDate")
    val routineStartDate: String,
    @SerialName("routineEndDate")
    val routineEndDate: String,
)

fun RoutineDto.toDomain(): Routine =
    Routine(
        id = this.routineId,
        name = this.routineName,
        repeatDays = this.repeatDay.map { DayOfWeek.fromString(it) },
        executionTime = this.executionTime,
        routineDate = this.routineDate,
        isComplete = this.routineCompleteYn,
        subRoutineNames = this.subRoutineNames,
        subRoutineIsCompleted = this.subRoutineCompleteYn,
        recommendedRoutineType = RecommendedRoutineType.fromString(this.recommendedRoutineType),
        isDeleted = routineDeletedYn,
        startDate = this.routineStartDate,
        endDate = this.routineEndDate,
    )
