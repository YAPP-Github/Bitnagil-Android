package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.Routines
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutinesUiModel(
    val routinesByDate: Map<String, List<RoutineUiModel>> = emptyMap(),
) : Parcelable

fun Routines.toUiModel(): RoutinesUiModel =
    RoutinesUiModel(
        routinesByDate = this.routinesByDate.mapValues { (_, routines) ->
            routines.map { it.toUiModel() }
        },
    )
