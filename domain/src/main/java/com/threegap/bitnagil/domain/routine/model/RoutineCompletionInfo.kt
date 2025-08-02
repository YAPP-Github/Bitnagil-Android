package com.threegap.bitnagil.domain.routine.model

data class RoutineCompletionInfo(
    val routineType: RoutineType,
    val routineId: String,
    val historySeq: Int,
    val isCompleted: Boolean,
)
