package com.threegap.bitnagil.domain.routine.model

data class RoutineCompletionInfos(
    val routineCompletionInfos: List<RoutineCompletionInfo>,
) {
    val hasCompletedRoutine: Boolean
        get() = routineCompletionInfos.any { it.routineCompleteYn }
}
