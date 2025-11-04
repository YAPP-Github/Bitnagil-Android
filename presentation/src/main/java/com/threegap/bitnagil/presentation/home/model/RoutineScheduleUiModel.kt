package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineScheduleUiModel(
    val dailyRoutines: Map<String, DayRoutinesUiModel> = emptyMap(),
) : Parcelable

fun RoutineSchedule.toUiModel(): RoutineScheduleUiModel =
    RoutineScheduleUiModel(
        dailyRoutines = this.dailyRoutines.mapValues { (_, dayRoutines) ->
            dayRoutines.toUiModel()
        },
    )
