package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType

sealed class WriteRoutineIntent : MviIntent {
    data class SetRoutineName(val name: String): WriteRoutineIntent()
    data object AddSubRoutine: WriteRoutineIntent()
    data class RemoveSubRoutine(val index: Int): WriteRoutineIntent()
    data class SetSubRoutineName(val index: Int, val name: String): WriteRoutineIntent()
    data class SetRepeatType(val repeatType: RepeatType): WriteRoutineIntent()
    data class SelectDay(val day: Day): WriteRoutineIntent()
    data class SetPeriodWeek(val periodWeek: Int): WriteRoutineIntent()
    data class SetStartTime(val time: Time): WriteRoutineIntent()
    data class SetWriteRoutineType(val writeRoutineType: WriteRoutineType): WriteRoutineIntent()
    data class SetRoutine(val name: String, val repeatDays: List<Day>, val startTime: Time, val subRoutines: List<String>): WriteRoutineIntent()
    data object SelectAllTime: WriteRoutineIntent()
    data object ShowTimePickerBottomSheet: WriteRoutineIntent()
    data object HideTimePickerBottomSheet: WriteRoutineIntent()
    data object RegisterRoutineLoading: WriteRoutineIntent()
    data object RegisterRoutineSuccess: WriteRoutineIntent()
    data object RegisterRoutineFailure: WriteRoutineIntent()
    data object EditRoutineLoading: WriteRoutineIntent()
    data object EditRoutineSuccess: WriteRoutineIntent()
    data object EditRoutineFailure: WriteRoutineIntent()
    data object GetRoutineLoading: WriteRoutineIntent()
}
