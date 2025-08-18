package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RecommendedRoutineType
import com.threegap.bitnagil.domain.routine.model.Routine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineUiModel(
    val routineId: String,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val routineDate: String,
    val routineCompleteYn: Boolean,
    val subRoutineNames: List<String>,
    val subRoutineCompleteYn: List<Boolean>,
    val recommendedRoutineType: RecommendedRoutineType?,
) : Parcelable

fun Routine.toUiModel(): RoutineUiModel =
    RoutineUiModel(
        routineId = this.routineId,
        routineName = this.routineName,
        repeatDay = this.repeatDay,
        executionTime = this.executionTime,
        routineDate = this.routineDate,
        routineCompleteYn = this.routineCompleteYn,
        subRoutineNames = this.subRoutineNames,
        subRoutineCompleteYn = this.subRoutineCompleteYn,
        recommendedRoutineType = this.recommendedRoutineType,
    )
