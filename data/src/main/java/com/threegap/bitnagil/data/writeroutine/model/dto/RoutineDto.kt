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
        val dividedTimeStrings = executionTime.split(":")
        require(dividedTimeStrings.size >= 2) { "Invalid time format: $executionTime Expected format: HH:mm / HH:mm:ss" }

        val startTime = Time(
            hour = dividedTimeStrings[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid hour: ${dividedTimeStrings[0]}"),
            minute = dividedTimeStrings[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid minute: ${dividedTimeStrings[1]}"),
        )

        return Routine(
            id = routineId,
            name = routineName,
            subRoutines = subRoutineInfos.map { it.toSubRoutine() },
            repeatDays = repeatDay.mapNotNull {
                try {
                    RepeatDay.valueOf(it)
                } catch (e: IllegalArgumentException) {
                    null
                }
            },
            startTime = startTime,
            endDate = Date(
                year = 2099,
                month = 12,
                day = 31,
            ),
        )
    }
}
