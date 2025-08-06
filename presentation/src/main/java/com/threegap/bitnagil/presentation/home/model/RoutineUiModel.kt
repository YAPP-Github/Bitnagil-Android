package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineByDayDeletion
import com.threegap.bitnagil.domain.routine.model.RoutineType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineUiModel(
    val routineId: String,
    val historySeq: Int,
    val repeatDay: List<DayOfWeek>,
    val routineName: String,
    val executionTime: String,
    val subRoutines: List<SubRoutineUiModel>,
    val isModified: Boolean = false,
    val routineCompletionId: Int?,
    val isCompleted: Boolean = false,
    val routineType: RoutineType,
) : Parcelable

fun Routine.toUiModel(): RoutineUiModel =
    RoutineUiModel(
        routineId = this.routineId,
        historySeq = this.historySeq,
        repeatDay = this.repeatDay,
        routineName = this.routineName,
        executionTime = this.executionTime,
        subRoutines = this.subRoutines.map { it.toUiModel() },
        isModified = this.isModified,
        routineCompletionId = this.routineCompletionId,
        isCompleted = this.isCompleted,
        routineType = this.routineType,
    )

fun RoutineUiModel.toRoutineByDayDeletion(performedDate: String): RoutineByDayDeletion =
    RoutineByDayDeletion(
        routineCompletionId = this.routineCompletionId,
        routineId = this.routineId,
        routineType = this.routineType,
        subRoutineInfosForDelete = this.subRoutines.map { it.toSubRoutineDeletionInfo() },
        performedDate = performedDate,
        historySeq = this.historySeq,
    )
