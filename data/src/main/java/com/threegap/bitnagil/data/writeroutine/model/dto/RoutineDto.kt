package com.threegap.bitnagil.data.writeroutine.model.dto

import com.threegap.bitnagil.domain.writeroutine.model.Date
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.Routine
import com.threegap.bitnagil.domain.writeroutine.model.Time
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
    @SerialName("subRoutineInfos")
    val subRoutineInfos: List<SubRoutineDto>,
) {
    fun toRoutine(): Routine {
        val startTime = Time(
            hour = executionTime.split(":")[0].toInt(),
            minute = executionTime.split(":")[1].toInt(),
        )

        return Routine(
            id = routineId,
            name = routineName,
            subRoutines = subRoutineInfos.map { it.toSubRoutine() },
            repeatDays = repeatDay.map { RepeatDay.valueOf(it) },
            startTime = startTime,
            endDate = Date(
                year = 2099,
                month = 12,
                day = 31,
            ),
        )
    }
}
