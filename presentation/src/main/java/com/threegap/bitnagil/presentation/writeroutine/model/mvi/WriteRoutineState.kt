package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.writeroutine.model.Date
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.SelectableDay
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType
import kotlinx.parcelize.Parcelize

@Parcelize
data class WriteRoutineState(
    val routineName: String,
    val subRoutineNames: List<String>,
    val selectNotUseSUbRoutines: Boolean,
    val repeatType: RepeatType?,
    val repeatDays: List<SelectableDay>,
    val startTime: Time?,
    val startDate: Date?,
    val endDate: Date?,
    val selectAllTime: Boolean,
    val loading: Boolean,
    val showTimePickerBottomSheet: Boolean,
    val showStartDatePickerBottomSheet: Boolean,
    val showEndDatePickerBottomSheet: Boolean,
    val writeRoutineType: WriteRoutineType,
    val subRoutineUiExpanded: Boolean,
    val repeatDaysUiExpanded: Boolean,
    val periodUiExpanded: Boolean,
    val startTimeUiExpanded: Boolean,
) : MviState {
    companion object {
        val Init = WriteRoutineState(
            routineName = "",
            subRoutineNames = listOf("", "", ""),
            selectNotUseSUbRoutines = false,
            repeatType = null,
            repeatDays = listOf(
                SelectableDay(
                    day = Day.MON,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.TUE,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.WED,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.THU,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.FRI,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.SAT,
                    selected = false,
                ),
                SelectableDay(
                    day = Day.SUN,
                    selected = false,
                ),
            ),
            startTime = null,
            selectAllTime = false,
            loading = false,
            showStartDatePickerBottomSheet = false,
            showEndDatePickerBottomSheet = false,
            showTimePickerBottomSheet = false,
            writeRoutineType = WriteRoutineType.ADD,
            subRoutineUiExpanded = false,
            repeatDaysUiExpanded = false,
            periodUiExpanded = false,
            startTimeUiExpanded = false,
            startDate = null,
            endDate = null,
        )
    }

    val registerButtonEnabled: Boolean
        get() = routineName.isNotEmpty() &&
            (repeatType == RepeatType.DAILY || (repeatType == RepeatType.DAY && repeatDays.any { it.selected })) &&
            startTime != null && !loading

    val subRoutinesText: String get() = subRoutineNames.filter { it.isNotEmpty() }.joinToString(separator = "\n")

    val repeatDaysText: String
        get() = when (repeatType) {
            RepeatType.DAILY -> "매일"
            RepeatType.DAY -> "매주 ${repeatDays.filter { it.selected }.joinToString { it.day.text }}"
            null -> ""
        }

    val periodText: String get() {
        if (startDate == null && endDate == null) return ""
        return "${startDate?.toYearShrinkageFormattedString() ?: ""} ~ ${endDate?.toYearShrinkageFormattedString() ?: ""}"
    }

    val startTimeText: String
        get() = if (selectAllTime) "하루종일" else startTime?.let { "${it.toAmPmFormattedString()}부터 시작" } ?: ""
}
