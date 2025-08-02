package com.threegap.bitnagil.domain.routine.model

data class RoutineCompletion(
    val performedDate: String,
    val routineCompletions: List<RoutineCompletionInfo>,
)
