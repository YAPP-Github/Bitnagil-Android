package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.SelectableDay
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import kotlinx.parcelize.Parcelize

@Parcelize
data class WriteRoutineState(
    val routineName: String,
    val subRoutines: List<String>,
    val repeatType: RepeatType?,
    val repeatDays: List<SelectableDay>,
    val periodWeek: Int?,
    val startTime: Time?,
    val selectAllTime: Boolean,
    val loading: Boolean,
    val showTimePickerBottomSheet: Boolean,
): MviState {
    companion object {
        val Init = WriteRoutineState(
            routineName = "",
            subRoutines = emptyList(),
            repeatType = null,
            repeatDays = listOf(
                SelectableDay(
                    day = Day.MON,
                    selected = false
                ),
                SelectableDay(
                    day = Day.TUE,
                    selected = false
                ),
                SelectableDay(
                    day = Day.WED,
                    selected = false
                ),
                SelectableDay(
                    day = Day.THU,
                    selected = false
                ),
                SelectableDay(
                    day = Day.FRI,
                    selected = false
                ),
                SelectableDay(
                    day = Day.SAT,
                    selected = false
                ),
                SelectableDay(
                    day = Day.SUN,
                    selected = false
                )
            ),
            periodWeek = null,
            startTime = null,
            selectAllTime = false,
            loading = false,
            showTimePickerBottomSheet = false,
        )
    }
}
