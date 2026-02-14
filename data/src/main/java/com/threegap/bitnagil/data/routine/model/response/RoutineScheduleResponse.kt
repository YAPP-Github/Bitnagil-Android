package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineScheduleResponse(
    @SerialName("routines")
    val dailyRoutines: Map<String, DailyRoutinesResponse>,
)

fun RoutineScheduleResponse.toDomain(): RoutineSchedule =
    RoutineSchedule(
        dailyRoutines = this.dailyRoutines.mapValues { (_, dailyRoutinesResponse) -> dailyRoutinesResponse.toDomain() },
    )
