package com.threegap.bitnagil.presentation.writeroutine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.recommendroutine.usecase.GetRecommendRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.GetRoutineUseCase
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.usecase.EditRoutineUseCase
import com.threegap.bitnagil.domain.writeroutine.usecase.GetChangedSubRoutinesUseCase
import com.threegap.bitnagil.domain.writeroutine.usecase.RegisterRoutineUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.SelectableDay
import com.threegap.bitnagil.presentation.writeroutine.model.SubRoutine
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineIntent
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineSideEffect
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineState
import com.threegap.bitnagil.presentation.writeroutine.model.navarg.WriteRoutineScreenArg
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine as DomainSubRoutine

@HiltViewModel(assistedFactory = WriteRoutineViewModel.Factory::class)
class WriteRoutineViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChangedSubRoutinesUseCase: GetChangedSubRoutinesUseCase,
    private val registerRoutineUseCase: RegisterRoutineUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val getRoutineUseCase: GetRoutineUseCase,
    private val getRecommendRoutineUseCase: GetRecommendRoutineUseCase,
    @Assisted private val writeRoutineArg: WriteRoutineScreenArg,
) : MviViewModel<WriteRoutineState, WriteRoutineSideEffect, WriteRoutineIntent>(
    initState = WriteRoutineState.Init,
    savedStateHandle = savedStateHandle,
) {
    @AssistedFactory interface Factory {
        fun create(writeRoutineArg: WriteRoutineScreenArg): WriteRoutineViewModel
    }

    private var routineId: String? = null
    private var oldSubRoutines: List<SubRoutine> = listOf()

    init {
        val navigationArg = writeRoutineArg
        initResource(navigationArg)
    }

    private fun initResource(navigationArg: WriteRoutineScreenArg) {
        when (navigationArg) {
            is WriteRoutineScreenArg.Add -> {
                viewModelScope.launch {
                    sendIntent(WriteRoutineIntent.SetWriteRoutineType(WriteRoutineType.ADD))
                }

                navigationArg.baseRoutineId?.let {
                    routineId = it
                    loadRecommendRoutine(it)
                }
            }
            is WriteRoutineScreenArg.Edit -> {
                viewModelScope.launch {
                    sendIntent(WriteRoutineIntent.SetWriteRoutineType(WriteRoutineType.EDIT))
                }

                navigationArg.routineId.also {
                    routineId = it
                    loadRoutine(it)
                }
            }
        }
    }

    private fun loadRoutine(routineId: String) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.GetRoutineLoading)
            getRoutineUseCase(routineId).fold(
                onSuccess = { routine ->
                    oldSubRoutines = routine.subRoutines.map { SubRoutine.fromDomainSubRoutine(it) }
                    sendIntent(
                        WriteRoutineIntent.SetRoutine(
                            name = routine.routineName,
                            repeatDays = routine.repeatDay.map { Day.fromDayOfWeek(it) },
                            startTime = Time.fromDomainTimeString(routine.executionTime),
                            subRoutines = routine.subRoutines.map { it.subRoutineName },
                        ),
                    )
                },
                onFailure = {
                    // 실패 케이스 처리 예정
                },
            )
        }
    }

    private fun loadRecommendRoutine(recommendRoutineId: String) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.GetRoutineLoading)
            getRecommendRoutineUseCase(recommendRoutineId).fold(
                onSuccess = { routine ->
                    oldSubRoutines = routine.recommendSubRoutines.map { SubRoutine.fromDomainRecommendSubRoutine(it) }
                    sendIntent(
                        WriteRoutineIntent.SetRoutine(
                            name = routine.name,
                            repeatDays = listOf(),
                            startTime = Time.fromDomainTimeString(routine.executionTime),
                            subRoutines = oldSubRoutines.map { it.name },
                        ),
                    )
                },
                onFailure = {
                    // 실패 케이스 처리 예정
                },
            )
        }
    }

    override suspend fun SimpleSyntax<WriteRoutineState, WriteRoutineSideEffect>.reduceState(
        intent: WriteRoutineIntent,
        state: WriteRoutineState,
    ): WriteRoutineState {
        when (intent) {
            WriteRoutineIntent.AddSubRoutine -> {
                return state.copy(
                    subRoutineNames = state.subRoutineNames + "",
                )
            }
            is WriteRoutineIntent.RemoveSubRoutine -> {
                return state.copy(
                    subRoutineNames = state.subRoutineNames.filterIndexed { index, _ ->
                        index != intent.index
                    },
                )
            }
            WriteRoutineIntent.SelectAllTime -> {
                return state.copy(
                    selectAllTime = !state.selectAllTime,
                    startTime = Time.AllDay,
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
                    },
                )
            }
            is WriteRoutineIntent.SetRepeatType -> {
                return state.copy(
                    repeatType = intent.repeatType,
                )
            }
            is WriteRoutineIntent.SetRoutineName -> {
                return state.copy(
                    routineName = intent.name,
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
                    subRoutineNames = state.subRoutineNames.mapIndexed { index, subRoutine ->
                        if (index == intent.index) {
                            intent.name
                        } else {
                            subRoutine
                        }
                    },
                )
            }
            WriteRoutineIntent.ShowTimePickerBottomSheet -> {
                return state.copy(
                    showTimePickerBottomSheet = true,
                )
            }
            WriteRoutineIntent.HideTimePickerBottomSheet -> {
                return state.copy(
                    showTimePickerBottomSheet = false,
                )
            }
            is WriteRoutineIntent.SetWriteRoutineType -> {
                return state.copy(
                    writeRoutineType = intent.writeRoutineType,
                )
            }

            WriteRoutineIntent.EditRoutineFailure -> {
                return state.copy(
                    loading = false,
                )
            }
            WriteRoutineIntent.EditRoutineSuccess -> {
                sendSideEffect(WriteRoutineSideEffect.MoveToPreviousScreen)

                return state.copy(
                    loading = false,
                )
            }
            WriteRoutineIntent.EditRoutineLoading -> {
                return state.copy(
                    loading = true,
                )
            }
            WriteRoutineIntent.RegisterRoutineFailure -> {
                return state.copy(
                    loading = false,
                )
            }
            WriteRoutineIntent.RegisterRoutineSuccess -> {
                sendSideEffect(WriteRoutineSideEffect.MoveToPreviousScreen)

                return state.copy(
                    loading = false,
                )
            }
            WriteRoutineIntent.RegisterRoutineLoading -> {
                return state.copy(
                    loading = true,
                )
            }

            is WriteRoutineIntent.SetRoutine -> {
                val selectedDaySet = intent.repeatDays.toSet()
                val repeatDays = SelectableDay.defaultList.map {
                    it.copy(
                        selected = it.day in selectedDaySet,
                    )
                }
                val repeatType = if (repeatDays.none { it.selected }) {
                    null
                } else if (repeatDays.all { it.selected }) {
                    RepeatType.DAILY
                } else {
                    RepeatType.DAY
                }
                return state.copy(
                    routineName = intent.name,
                    repeatDays = repeatDays,
                    repeatType = repeatType,
                    startTime = intent.startTime,
                    subRoutineNames = intent.subRoutines,
                    loading = false,
                )
            }

            WriteRoutineIntent.GetRoutineLoading -> {
                return state.copy(
                    loading = true,
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

    fun removeSubRoutine(index: Int) {
        viewModelScope.launch {
            sendIntent(WriteRoutineIntent.RemoveSubRoutine(index))
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

    fun registerRoutine() {
        viewModelScope.launch {
            val currentState = stateFlow.value

            val startTime = currentState.startTime ?: return@launch

            val repeatDay = when (currentState.repeatType) {
                RepeatType.DAILY -> listOf(
                    RepeatDay.MON,
                    RepeatDay.TUE,
                    RepeatDay.WED,
                    RepeatDay.THU,
                    RepeatDay.FRI,
                    RepeatDay.SAT,
                    RepeatDay.SUN,
                )

                RepeatType.DAY ->
                    currentState.repeatDays
                        .filter { it.selected }
                        .map { it.day.toRepeatDay() }

                null -> return@launch
            }

            when (currentState.writeRoutineType) {
                WriteRoutineType.ADD -> {
                    sendIntent(WriteRoutineIntent.RegisterRoutineLoading)
                    val registerRoutineResult = registerRoutineUseCase(
                        currentState.routineName,
                        repeatDay,
                        startTime.toDomainTime(),
                        currentState.subRoutineNames,
                    )

                    if (registerRoutineResult.isSuccess) {
                        sendIntent(WriteRoutineIntent.RegisterRoutineSuccess)
                    } else {
                        sendIntent(WriteRoutineIntent.RegisterRoutineFailure)
                    }
                }
                WriteRoutineType.EDIT -> {
                    val currentRoutineId = routineId ?: return@launch

                    val subRoutineDiffs = getChangedSubRoutinesUseCase(
                        oldSubRoutines = oldSubRoutines.mapIndexed { index, subRoutine ->
                            DomainSubRoutine(
                                id = subRoutine.id,
                                name = subRoutine.name,
                                sort = index + 1,
                            )
                        },
                        newSubRoutineNames = currentState.subRoutineNames,
                    )

                    sendIntent(WriteRoutineIntent.EditRoutineLoading)
                    val editRoutineResult = editRoutineUseCase(
                        routineId = currentRoutineId,
                        name = currentState.routineName,
                        repeatDay,
                        startTime = startTime.toDomainTime(),
                        subRoutines = subRoutineDiffs,
                    )

                    if (editRoutineResult.isSuccess) {
                        sendIntent(WriteRoutineIntent.EditRoutineSuccess)
                    } else {
                        sendIntent(WriteRoutineIntent.EditRoutineFailure)
                    }
                }
            }
        }
    }
}
