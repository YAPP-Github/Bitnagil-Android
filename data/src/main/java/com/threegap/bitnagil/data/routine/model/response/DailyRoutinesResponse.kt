package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.routine.model.DailyRoutines
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyRoutinesResponse(
    @SerialName("routineList")
    val routines: List<RoutineResponse>,
    @SerialName("allCompleted")
    val allCompleted: Boolean,
)

fun DailyRoutinesResponse.toDomain(): DailyRoutines =
    DailyRoutines(
        routines = routines.map { it.toDomain() },
        isAllCompleted = allCompleted,
    )
