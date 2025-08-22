package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.writeroutine.model.Date
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType

sealed class WriteRoutineIntent : MviIntent {
    data class SetRoutineName(val name: String) : WriteRoutineIntent()
    data class SetSubRoutineName(val index: Int, val name: String) : WriteRoutineIntent()
    data object SelectNotUseSubRoutines : WriteRoutineIntent()
    data class SetRepeatType(val repeatType: RepeatType) : WriteRoutineIntent()
    data class SelectDay(val day: Day) : WriteRoutineIntent()
    data class SetStartTime(val time: Time) : WriteRoutineIntent()
    data class SetWriteRoutineType(val writeRoutineType: WriteRoutineType) : WriteRoutineIntent()
    data class SetRoutine(
        val name: String,
        val repeatDays: List<Day>,
        val startTime: Time,
        val subRoutines: List<String>,
        val startDate: Date,
        val endDate: Date,
        val recommendedRoutineType: String?,
    ) : WriteRoutineIntent()
    data object SelectAllTime : WriteRoutineIntent()
    data object ShowTimePickerBottomSheet : WriteRoutineIntent()
    data object HideTimePickerBottomSheet : WriteRoutineIntent()
    data object ShowStartDatePickerBottomSheet : WriteRoutineIntent()
    data object HideStartDatePickerBottomSheet : WriteRoutineIntent()
    data object ShowEndDatePickerBottomSheet : WriteRoutineIntent()
    data object HideEndDatePickerBottomSheet : WriteRoutineIntent()
    data class SetSubRoutineUiExpanded(val expanded: Boolean) : WriteRoutineIntent()
    data class SetRepeatDaysUiExpanded(val expanded: Boolean) : WriteRoutineIntent()
    data class SetPeriodUiExpanded(val expanded: Boolean) : WriteRoutineIntent()
    data class SetStartTimeUiExpanded(val expanded: Boolean) : WriteRoutineIntent()
    data class SetStartDate(val date: Date) : WriteRoutineIntent()
    data class SetEndDate(val date: Date) : WriteRoutineIntent()
    data object RegisterRoutineLoading : WriteRoutineIntent()
    data object RegisterRoutineSuccess : WriteRoutineIntent()
    data object RegisterRoutineFailure : WriteRoutineIntent()
    data object EditRoutineLoading : WriteRoutineIntent()
    data object EditRoutineSuccess : WriteRoutineIntent()
    data object EditRoutineFailure : WriteRoutineIntent()
    data object GetRoutineLoading : WriteRoutineIntent()
}
