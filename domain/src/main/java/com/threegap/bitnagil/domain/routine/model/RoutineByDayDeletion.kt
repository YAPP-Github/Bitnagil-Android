package com.threegap.bitnagil.domain.routine.model

data class RoutineByDayDeletion(
    val routineCompletionId: Int?,
    val routineId: String,
    val routineType: RoutineType,
    val subRoutineInfosForDelete: List<SubRoutineDeletionInfo>,
    val performedDate: String,
    val historySeq: Int,
)
