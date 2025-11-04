package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DayRoutines
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayRoutinesUiModel(
    val routines: List<RoutineUiModel> = emptyList(),
    val isAllCompleted: Boolean = false,
) : Parcelable

fun DayRoutines.toUiModel(): DayRoutinesUiModel =
    DayRoutinesUiModel(
        routines = routines.map { it.toUiModel() },
        isAllCompleted = isAllCompleted,
    )
