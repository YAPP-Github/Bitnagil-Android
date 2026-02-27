package com.threegap.bitnagil.presentation.screen.home.model

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule

data class RoutineScheduleUiModel(
    val dailyRoutines: Map<String, DailyRoutinesUiModel>,
) {
    companion object {
        val INIT = RoutineScheduleUiModel(dailyRoutines = emptyMap())
    }
}

internal fun RoutineSchedule.toUiModel(): RoutineScheduleUiModel =
    RoutineScheduleUiModel(
        dailyRoutines = this.dailyRoutines.mapValues { (_, dayRoutines) -> dayRoutines.toUiModel() },
    )
