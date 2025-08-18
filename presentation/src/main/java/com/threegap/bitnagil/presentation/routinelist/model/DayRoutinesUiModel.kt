package com.threegap.bitnagil.presentation.routinelist.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DayRoutines
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayRoutinesUiModel(
    val routineList: List<RoutineUiModel> = emptyList(),
) : Parcelable

fun DayRoutines.toUiModel(): DayRoutinesUiModel =
    DayRoutinesUiModel(
        routineList = routineList.map { it.toUiModel() },
    )
