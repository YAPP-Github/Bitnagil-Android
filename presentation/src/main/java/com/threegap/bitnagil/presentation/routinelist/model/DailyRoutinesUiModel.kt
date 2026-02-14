package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.domain.routine.model.DailyRoutines

data class DailyRoutinesUiModel(
    val routines: List<RoutineUiModel>,
)

internal fun DailyRoutines.toUiModel(): DailyRoutinesUiModel =
    DailyRoutinesUiModel(
        routines = routines.map { it.toUiModel() },
    )
