package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.domain.routine.model.SubRoutine
import com.threegap.bitnagil.domain.routine.model.SubRoutineDeletionInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubRoutineUiModel(
    val subRoutineId: String,
    val historySeq: Int,
    val subRoutineName: String,
    val sortOrder: Int,
    val routineCompletionId: Int?,
    val isCompleted: Boolean = false,
    val isModified: Boolean = false,
    val routineType: RoutineType,
) : Parcelable

fun SubRoutine.toUiModel(): SubRoutineUiModel =
    SubRoutineUiModel(
        subRoutineId = this.subRoutineId,
        historySeq = this.historySeq,
        subRoutineName = this.subRoutineName,
        routineCompletionId = this.routineCompletionId,
        sortOrder = this.sortOrder,
        isCompleted = this.isCompleted,
        isModified = this.isModified,
        routineType = this.routineType,
    )

fun SubRoutineUiModel.toSubRoutineDeletionInfo(): SubRoutineDeletionInfo =
    SubRoutineDeletionInfo(
        routineCompletionId = this.routineCompletionId,
        subRoutineId = this.subRoutineId,
    )
