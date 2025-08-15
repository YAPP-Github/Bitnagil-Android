package com.threegap.bitnagil.domain.routine.model

data class RoutineCompletionInfo(
    val routineId: String,
    val routineCompleteYn: Boolean,
    val subRoutineCompleteYn: List<Boolean>,
)
