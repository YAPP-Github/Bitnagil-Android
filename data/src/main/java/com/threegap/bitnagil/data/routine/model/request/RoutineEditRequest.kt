package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineUpdateType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineEditRequest(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("updateApplyDate")
    val updateApplyDate: RoutineUpdateType,
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
)

fun RoutineEditInfo.toDto(): RoutineEditRequest {
    return RoutineEditRequest(
        routineId = this.id,
        updateApplyDate = this.updateType,
        routineName = this.name,
        repeatDay = this.repeatDay,
        routineStartDate = this.startDate.toString(),
        routineEndDate = this.endDate.toString(),
        executionTime = this.startTime.toString(),
        subRoutineName = this.subRoutines,
    )
}
