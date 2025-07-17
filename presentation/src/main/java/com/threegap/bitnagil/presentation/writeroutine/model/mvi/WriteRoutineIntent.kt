package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.Time

sealed class WriteRoutineIntent : MviIntent {
    data class SetRoutineName(val name: String): WriteRoutineIntent()
    data object AddSubRoutine: WriteRoutineIntent()
    data class RemoveSubRoutine(val index: Int): WriteRoutineIntent()
    data class SetSubRoutineName(val index: Int, val name: String): WriteRoutineIntent()
    data class SetRepeatType(val repeatType: RepeatType): WriteRoutineIntent()
    data class SelectDay(val day: Day): WriteRoutineIntent()
    data class SetPeriodWeek(val periodWeek: Int): WriteRoutineIntent()
    data class SetStartTime(val time: Time): WriteRoutineIntent()
    data object SelectAllTime: WriteRoutineIntent()
}
