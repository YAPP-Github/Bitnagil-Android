package com.threegap.bitnagil.domain.writeroutine.model

import java.time.LocalDate
import java.time.LocalTime

data class Routine(
    val id: String,
    val name: String,
    val subRoutines: List<SubRoutine>,
    val repeatDays: List<RepeatDay>,
    val endDate: LocalDate,
    val startTime: LocalTime,
)
