package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DailyRoutines
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyRoutinesUiModel(
    val routines: List<RoutineUiModel> = emptyList(),
    val isAllCompleted: Boolean = false,
) : Parcelable

fun DailyRoutines.toUiModel(): DailyRoutinesUiModel =
    DailyRoutinesUiModel(
        routines = routines.map { it.toUiModel() },
        isAllCompleted = isAllCompleted,
    )
