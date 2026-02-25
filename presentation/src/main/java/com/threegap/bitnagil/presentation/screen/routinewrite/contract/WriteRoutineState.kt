package com.threegap.bitnagil.presentation.screen.routinewrite.contract

import android.os.Parcelable
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.presentation.screen.routinewrite.model.Date
import com.threegap.bitnagil.presentation.screen.routinewrite.model.Day
import com.threegap.bitnagil.presentation.screen.routinewrite.model.RepeatType
import com.threegap.bitnagil.presentation.screen.routinewrite.model.SelectableDay
import com.threegap.bitnagil.presentation.screen.routinewrite.model.Time
import com.threegap.bitnagil.presentation.screen.routinewrite.model.WriteRoutineType
import kotlinx.parcelize.Parcelize

@Parcelize
data class WriteRoutineState(
    val routineName: String,
    val subRoutineNames: List<String>,
    val selectNotUseSubRoutines: Boolean,
    val repeatType: RepeatType?,
    val repeatDays: List<SelectableDay>,
    val startTime: Time?,
    val startDate: Date,
    val endDate: Date,
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
    val recommendedRoutineType: RecommendCategory?,
) : Parcelable {
    companion object {
        const val MAX_ROUTINE_NAME_LENGTH = 20

        val INIT = WriteRoutineState(
            routineName = "",
            subRoutineNames = listOf("", "", ""),
            selectNotUseSubRoutines = false,
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
            writeRoutineType = WriteRoutineType.Add,
            subRoutineUiExpanded = false,
            repeatDaysUiExpanded = false,
            periodUiExpanded = false,
            startTimeUiExpanded = false,
            startDate = Date.now(),
            endDate = Date.now(),
            recommendedRoutineType = null,
        )
    }

    val registerButtonEnabled: Boolean
        get() = routineName.isNotEmpty() && startTime != null && !loading

    val subRoutinesText: String get() = subRoutineNames.filter { it.isNotEmpty() }.joinToString(separator = "\n")

    val repeatDaysText: String
        get() = when (repeatType) {
            RepeatType.DAILY -> "매일"
            RepeatType.DAY -> if (repeatDays.none { it.selected }) "반복 안함" else "매주 ${repeatDays.filter { it.selected }.joinToString { it.day.text }}"
            null -> ""
        }

    val periodText: String
        get() = "${startDate.toYearShrinkageFormattedString()} ~ ${endDate.toYearShrinkageFormattedString()}"

    val startTimeText: String
        get() = if (selectAllTime) "하루종일" else startTime?.let { "${it.toAmPmFormattedString()}부터 시작" } ?: ""
}
