package com.threegap.bitnagil.domain.routine.model

/**
 * 특정 기간(예: 주간, 월간) 동안의 루틴 일정표.
 *
 * @property dailyRoutines 날짜(String, "YYYY-MM-DD")를 key로,
 * 해당 날짜의 루틴 정보([DayRoutines])를  value로 가지는 Map.
 */
data class RoutineSchedule(
    val dailyRoutines: Map<String, DayRoutines>,
)
