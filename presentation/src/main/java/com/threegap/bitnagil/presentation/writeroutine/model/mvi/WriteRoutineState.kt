package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
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
): MviState {
    companion object {
        val Init = WriteRoutineState(
            routineName = "",
            subRoutines = emptyList(),
            repeatType = null,
            repeatDays = emptyList(),
            periodWeek = null,
            startTime = null,
            selectAllTime = false,
            loading = false,
        )
    }
}
