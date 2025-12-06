package com.threegap.bitnagil.presentation.routinelist.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutinesUiModel(
    val routines: Map<String, DayRoutinesUiModel> = emptyMap(),
) : Parcelable

fun RoutineSchedule.toUiModel(): RoutinesUiModel =
    RoutinesUiModel(
        routines = this.dailyRoutines.mapValues { (_, dayRoutines) ->
            dayRoutines.toUiModel()
        },
    )
