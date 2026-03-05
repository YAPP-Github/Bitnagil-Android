package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineRegisterRequest(
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<DayOfWeek>,
    @SerialName("routineStartDate")
    val routineStartDate: String,
    @SerialName("routineEndDate")
    val routineEndDate: String,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineName")
    val subRoutineName: List<String>,
    @SerialName("recommendedRoutineType")
    val recommendedRoutineType: RecommendCategory?,
)

fun RoutineRegisterInfo.toDto(): RoutineRegisterRequest {
    return RoutineRegisterRequest(
        routineName = this.name,
        repeatDay = this.repeatDay,
        routineStartDate = this.startDate.toString(),
        routineEndDate = this.endDate.toString(),
        executionTime = this.startTime.toString(),
        subRoutineName = this.subRoutines,
        recommendedRoutineType = this.recommendedRoutineType,
    )
}
