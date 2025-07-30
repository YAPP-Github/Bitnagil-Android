package com.threegap.bitnagil.domain.routine.model

data class Routine(
    val routineId: String,
    val historySeq: Int,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val subRoutines: List<SubRoutine>,
    val isModified: Boolean,
    val routineCompletionId: Int?,
    val isCompleted: Boolean,
    val routineType: RoutineType,
) {
    fun withSortedSubRoutines(): Routine =
        copy(subRoutines = subRoutines.sortedBy { it.sortOrder })
}
