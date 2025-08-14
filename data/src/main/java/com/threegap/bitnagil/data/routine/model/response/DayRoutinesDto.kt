package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.routine.model.DayRoutines
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayRoutinesDto(
    @SerialName("routineList")
    val routineList: List<RoutineDto>,
    @SerialName("allCompleted")
    val allCompleted: Boolean,
)

fun DayRoutinesDto.toDomain(): DayRoutines =
    DayRoutines(
        routineList = routineList.map { it.toDomain() },
        allCompleted = allCompleted,
    )
