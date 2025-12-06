package com.threegap.bitnagil.presentation.routinelist.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DailyRoutines
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayRoutinesUiModel(
    val routineList: List<RoutineUiModel> = emptyList(),
) : Parcelable

fun DailyRoutines.toUiModel(): DayRoutinesUiModel =
    DayRoutinesUiModel(
        routineList = routines.map { it.toUiModel() },
    )
