package com.threegap.bitnagil.domain.routine.model

data class RoutineToggleState(
    val isCompleted: Boolean,
    val subRoutinesIsCompleted: List<Boolean>,
)
