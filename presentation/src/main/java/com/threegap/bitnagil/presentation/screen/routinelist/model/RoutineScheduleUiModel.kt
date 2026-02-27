package com.threegap.bitnagil.presentation.screen.routinelist.model

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule

data class RoutineScheduleUiModel(
    val routines: Map<String, DailyRoutinesUiModel>,
) {
    companion object {
        val INIT = RoutineScheduleUiModel(
            routines = emptyMap(),
        )
    }
}

internal fun RoutineSchedule.toUiModel(): RoutineScheduleUiModel =
    RoutineScheduleUiModel(
        routines = this.dailyRoutines.mapValues { (_, dailyRoutines) -> dailyRoutines.toUiModel() },
    )
