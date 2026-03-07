package com.threegap.bitnagil.domain.routine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import java.time.LocalDate
import java.time.LocalTime

data class RoutineRegisterInfo(
    val name: String,
    val repeatDay: List<DayOfWeek>,
    val startTime: LocalTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val subRoutines: List<String>,
    val recommendedRoutineType: RecommendCategory?,
)
