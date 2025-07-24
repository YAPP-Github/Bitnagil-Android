package com.threegap.bitnagil.domain.routine.model

data class SubRoutine(
    val subRoutineId: String,
    val historySeq: Int,
    val subRoutineName: String,
    val isModified: Boolean,
    val sortOrder: Int,
    val isCompleted: Boolean,
    val routineType: RoutineType
)
