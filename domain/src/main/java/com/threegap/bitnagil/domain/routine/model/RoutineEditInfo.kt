package com.threegap.bitnagil.domain.routine.model

import java.time.LocalDate
import java.time.LocalTime

data class RoutineEditInfo(
    val id: String,
    val updateType: RoutineUpdateType,
    val name: String,
    val repeatDay: List<DayOfWeek>,
    val startTime: LocalTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val subRoutines: List<String>,
)
