package com.threegap.bitnagil.presentation.screen.home.model

import com.threegap.bitnagil.domain.routine.model.DailyRoutines

data class DailyRoutinesUiModel(
    val routines: List<RoutineUiModel>,
    val isAllCompleted: Boolean,
)

internal fun DailyRoutines.toUiModel(): DailyRoutinesUiModel =
    DailyRoutinesUiModel(
        routines = routines.map { it.toUiModel() },
        isAllCompleted = isAllCompleted,
    )
