package com.threegap.bitnagil.domain.routine.model

data class Routine(
    val routineId: String,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val startDate: String,
    val endDate: String,
    val routineDate: String,
    val routineCompleteYn: Boolean,
    val subRoutineNames: List<String>,
    val subRoutineCompleteYn: List<Boolean>,
    val recommendedRoutineType: RecommendedRoutineType?,
)
