package com.threegap.bitnagil.domain.routine.model

data class RoutineToggleState(
    val subRoutinesIsCompleted: List<Boolean>,
) {
    val isCompleted: Boolean
        get() = subRoutinesIsCompleted.all { it }
}
