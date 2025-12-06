package com.threegap.bitnagil.domain.routine.model

data class Routine(
    val id: String,
    val name: String,
    val repeatDays: List<DayOfWeek>,
    val executionTime: String,
    val startDate: String,
    val endDate: String,
    val routineDate: String,
    val isCompleted: Boolean,
    val isDeleted: Boolean,
    val subRoutineNames: List<String>,
    val subRoutineCompletionStates: List<Boolean>,
    val recommendedRoutineType: RecommendedRoutineType?,
)
