package com.threegap.bitnagil.presentation.screen.home.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine

data class RoutineUiModel(
    val id: String,
    val name: String,
    val repeatDays: List<DayOfWeek>,
    val executionTime: String,
    val routineDate: String,
    val isCompleted: Boolean,
    val subRoutineNames: List<String>,
    val subRoutineCompletionStates: List<Boolean>,
    val recommendedRoutineType: RecommendCategory?,
)

internal fun Routine.toUiModel(): RoutineUiModel =
    RoutineUiModel(
        id = this.id,
        name = this.name,
        repeatDays = this.repeatDays,
        executionTime = this.executionTime,
        routineDate = this.routineDate,
        isCompleted = this.isCompleted,
        subRoutineNames = this.subRoutineNames,
        subRoutineCompletionStates = this.subRoutineCompletionStates,
        recommendedRoutineType = this.recommendedRoutineType,
    )
