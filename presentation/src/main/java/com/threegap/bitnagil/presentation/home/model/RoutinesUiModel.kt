package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.Routines
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutinesUiModel(
    val routines: Map<String, DayRoutinesUiModel> = emptyMap(),
) : Parcelable

fun Routines.toUiModel(): RoutinesUiModel =
    RoutinesUiModel(
        routines = this.routines.mapValues { (_, dayRoutines) ->
            dayRoutines.toUiModel()
        },
    )
