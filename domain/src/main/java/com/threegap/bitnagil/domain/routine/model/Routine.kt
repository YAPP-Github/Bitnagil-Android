package com.threegap.bitnagil.domain.routine.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Routine(
    val routineId: String,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val routineDate: String,
    val routineCompleteYn: Boolean,
    val subRoutineNames: List<String>,
    val subRoutineCompleteYn: List<Boolean>,
    val recommendedRoutineType: RecommendedRoutineType?,
) {
    private val koreanLocale = Locale.KOREAN
    private val executionTimeFormatter12 = DateTimeFormatter.ofPattern("a h:mm", koreanLocale)

    val formattedExecutionTime: String =
        try {
            val time = LocalTime.parse(executionTime)
            if (time == LocalTime.MIDNIGHT) {
                "하루종일"
            } else {
                time.format(executionTimeFormatter12)
            }
        } catch (e: Exception) {
            "시간 미정"
        }
}
