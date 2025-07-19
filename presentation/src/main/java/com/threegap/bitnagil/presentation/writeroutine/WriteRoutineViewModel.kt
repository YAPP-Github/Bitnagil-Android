package com.threegap.bitnagil.presentation.writeroutine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineIntent
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineSideEffect
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class WriteRoutineViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : MviViewModel<WriteRoutineState, WriteRoutineSideEffect, WriteRoutineIntent>(
    initState = WriteRoutineState.Init,
    savedStateHandle = savedStateHandle,
) {
    override suspend fun SimpleSyntax<WriteRoutineState, WriteRoutineSideEffect>.reduceState(
        intent: WriteRoutineIntent,
        state: WriteRoutineState,
    ): WriteRoutineState {
        when(intent) {
            WriteRoutineIntent.AddSubRoutine -> {
                return state.copy(
                    subRoutines = state.subRoutines + ""
                )
            }
            is WriteRoutineIntent.RemoveSubRoutine -> {
                return state.copy(
                    subRoutines = state.subRoutines.drop(intent.index)
                )
            }
            WriteRoutineIntent.SelectAllTime -> {
                return state.copy(
                    selectAllTime = !state.selectAllTime,
                    startTime = Time.AllDay
                )
            }
            is WriteRoutineIntent.SelectDay -> {
                return state.copy(
                    repeatDays = state.repeatDays.map {
                        if (it.day == intent.day) {
                            it.copy(selected = !it.selected)
                        } else {
                            it
                        }
                    }
                )
            }
            is WriteRoutineIntent.SetPeriodWeek -> {
                return state.copy(
                    periodWeek = intent.periodWeek
                )
            }
            is WriteRoutineIntent.SetRepeatType -> {
                return state.copy(
                    repeatType = intent.repeatType
                )
            }
            is WriteRoutineIntent.SetRoutineName -> {
                return state.copy(
                    routineName = intent.name
                )
            }
            is WriteRoutineIntent.SetStartTime -> {
                return state.copy(
                    selectAllTime = intent.time == Time.AllDay,
                    startTime = intent.time,
                )
            }
            is WriteRoutineIntent.SetSubRoutineName -> {
                return state.copy(
                    subRoutines = state.subRoutines.mapIndexed { index, subRoutine ->
                        if (index == intent.index) {
                            intent.name
                        } else {
                            subRoutine
                        }
                    }
                )
            }
            WriteRoutineIntent.ShowTimePickerBottomSheet -> {
                return state.copy(
                    showTimePickerBottomSheet = true
                )
            }
            WriteRoutineIntent.HideTimePickerBottomSheet -> {
                return state.copy(
                    showTimePickerBottomSheet = false
                )
            }
        }
    }

    fun setRoutineName(name: String) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SetRoutineName(name))
        }

    }

    fun setSubRoutineName(index: Int, name: String) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SetSubRoutineName(index, name))
        }
    }

    fun addSubRoutine() {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.AddSubRoutine)
        }
    }

    fun selectRepeatType(repeatType: RepeatType) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SetRepeatType(repeatType))
        }
    }

    fun selectDay(day: Day) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SelectDay(day))
        }
    }

    fun selectAllTime() {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SelectAllTime)
        }
    }

    fun setStartTime(hour: Int, minute: Int) {
        val time = Time(hour = hour, minute = minute)
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.SetStartTime(time))
        }
    }

    fun showTimePickerBottomSheet() {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.ShowTimePickerBottomSheet)
        }
    }

    fun hideTimePickerBottomSheet() {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.HideTimePickerBottomSheet)
        }
    }
}
