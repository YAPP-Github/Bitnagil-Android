package com.threegap.bitnagil.presentation.writeroutine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.recommendroutine.usecase.GetRecommendRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.GetRoutineUseCase
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.RoutineUpdateType
import com.threegap.bitnagil.domain.writeroutine.usecase.EditRoutineUseCase
import com.threegap.bitnagil.domain.writeroutine.usecase.RegisterRoutineUseCase
import com.threegap.bitnagil.presentation.writeroutine.contract.WriteRoutineSideEffect
import com.threegap.bitnagil.presentation.writeroutine.contract.WriteRoutineState
import com.threegap.bitnagil.presentation.writeroutine.model.Date
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.SelectableDay
import com.threegap.bitnagil.presentation.writeroutine.model.SubRoutineUiModel
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType
import com.threegap.bitnagil.presentation.writeroutine.model.navarg.WriteRoutineScreenArg
import com.threegap.bitnagil.presentation.writeroutine.model.toUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel(assistedFactory = WriteRoutineViewModel.Factory::class)
class WriteRoutineViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerRoutineUseCase: RegisterRoutineUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val getRoutineUseCase: GetRoutineUseCase,
    private val getRecommendRoutineUseCase: GetRecommendRoutineUseCase,
    @Assisted private val writeRoutineArg: WriteRoutineScreenArg,
) : ContainerHost<WriteRoutineState, WriteRoutineSideEffect>, ViewModel() {
    @AssistedFactory interface Factory {
        fun create(writeRoutineArg: WriteRoutineScreenArg): WriteRoutineViewModel
    }

    override val container: Container<WriteRoutineState, WriteRoutineSideEffect> = container(
        savedStateHandle = savedStateHandle,
        initialState = WriteRoutineState.INIT,
    )

    private var routineId: String? = null
    private var oldSubRoutineUiModels: List<SubRoutineUiModel> = listOf()

    init {
        val navigationArg = writeRoutineArg
        initResource(navigationArg)
    }

    private fun initResource(navigationArg: WriteRoutineScreenArg) = intent {
        when (navigationArg) {
            is WriteRoutineScreenArg.Add -> {
                reduce {
                    state.copy(
                        writeRoutineType = WriteRoutineType.Add,
                    )
                }

                navigationArg.baseRoutineId?.let {
                    routineId = it
                    loadRecommendRoutine(it)
                }
            }
            is WriteRoutineScreenArg.Edit -> {
                reduce {
                    state.copy(
                        writeRoutineType = WriteRoutineType.Edit(updateRoutineFromNowDate = navigationArg.updateRoutineFromNowDate),
                    )
                }

                navigationArg.routineId.also {
                    routineId = it
                    loadRoutine(it)
                }
            }
        }
    }

    private fun loadRoutine(routineId: String) = intent {
        reduce {
            state.copy(loading = true)
        }

        getRoutineUseCase(routineId).fold(
            onSuccess = { routine ->
                val selectedDaySet = routine.repeatDays.map { Day.fromDayOfWeek(it) }
                val repeatDays = SelectableDay.defaultList.map {
                    it.copy(selected = it.day in selectedDaySet)
                }
                val repeatType = if (repeatDays.none { it.selected }) {
                    null
                } else if (repeatDays.all { it.selected }) {
                    RepeatType.DAILY
                } else {
                    RepeatType.DAY
                }

                reduce {
                    state.copy(
                        routineName = routine.name,
                        repeatDays = repeatDays,
                        repeatType = repeatType,
                        startTime = Time.fromString(routine.executionTime),
                        startDate = Date.fromString(routine.startDate),
                        endDate = Date.fromString(routine.endDate),
                        subRoutineNames = listOf(
                            routine.subRoutineNames.getOrNull(0) ?: "",
                            routine.subRoutineNames.getOrNull(1) ?: "",
                            routine.subRoutineNames.getOrNull(2) ?: "",
                        ),
                        loading = false,
                        recommendedRoutineType = null,
                    )
                }
            },
            onFailure = {
                // 실패 케이스 처리 예정
            },
        )
    }

    private fun loadRecommendRoutine(recommendRoutineId: String) = intent {
        reduce {
            state.copy(loading = true)
        }
        getRecommendRoutineUseCase(recommendRoutineId).fold(
            onSuccess = { routine ->
                oldSubRoutineUiModels = routine.subRoutines.map { it.toUiModel() }

                reduce {
                    state.copy(
                        routineName = routine.name,
                        repeatDays = SelectableDay.defaultList,
                        repeatType = null,
                        startTime = Time.fromString(routine.executionTime),
                        startDate = Date.now(),
                        endDate = Date.now(),
                        subRoutineNames = listOf(
                            oldSubRoutineUiModels.getOrNull(0)?.name ?: "",
                            oldSubRoutineUiModels.getOrNull(1)?.name ?: "",
                            oldSubRoutineUiModels.getOrNull(2)?.name ?: "",
                        ),
                        loading = false,
                        recommendedRoutineType = routine.category,
                    )
                }
            },
            onFailure = {
                // 실패 케이스 처리 예정
            },
        )
    }

    fun setRoutineName(name: String) = intent {
        reduce {
            state.copy(
                routineName = name,
            )
        }
    }

    fun setSubRoutineName(index: Int, name: String) = intent {
        reduce {
            state.copy(
                subRoutineNames = state.subRoutineNames.mapIndexed { subRoutineIndex, subRoutine ->
                    if (subRoutineIndex == index) {
                        name
                    } else {
                        subRoutine
                    }
                },
            )
        }
    }

    fun selectNotUseSubRoutines() = intent {
        val toggledSelectNotUseSubRoutines = !state.selectNotUseSubRoutines
        reduce {
            state.copy(
                selectNotUseSubRoutines = toggledSelectNotUseSubRoutines,
                subRoutineNames = if (toggledSelectNotUseSubRoutines) listOf("", "", "") else state.subRoutineNames,
            )
        }
    }

    fun selectRepeatType(repeatType: RepeatType) = intent {
        reduce {
            state.copy(
                repeatType = repeatType,
            )
        }
    }

    fun selectDay(day: Day) = intent {
        reduce {
            state.copy(
                repeatDays = state.repeatDays.map {
                    if (it.day == day) {
                        it.copy(selected = !it.selected)
                    } else {
                        it
                    }
                },
            )
        }
    }

    fun selectAllTime() = intent {
        reduce {
            state.copy(
                selectAllTime = !state.selectAllTime,
                startTime = Time.AllDay,
            )
        }
    }

    fun setStartTime(hour: Int, minute: Int) = intent {
        val time = Time(hour = hour, minute = minute)

        reduce {
            state.copy(
                selectAllTime = time == Time.AllDay,
                startTime = time,
            )
        }
    }

    fun showTimePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showTimePickerBottomSheet = true,
            )
        }
    }

    fun hideTimePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showTimePickerBottomSheet = false,
            )
        }
    }

    fun showStartDatePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showStartDatePickerBottomSheet = true,
            )
        }
    }

    fun hideStartDatePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showStartDatePickerBottomSheet = false,
            )
        }
    }

    fun showEndDatePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showEndDatePickerBottomSheet = true,
            )
        }
    }

    fun hideEndDatePickerBottomSheet() = intent {
        reduce {
            state.copy(
                showEndDatePickerBottomSheet = false,
            )
        }
    }

    fun setStartDate(date: Date) = intent {
        reduce {
            state.copy(
                startDate = date,
                endDate = Date.max(date, state.endDate),
            )
        }
    }

    fun setEndDate(date: Date) = intent {
        reduce {
            state.copy(
                startDate = Date.min(date, state.startDate),
                endDate = date,
            )
        }
    }

    fun toggleSubRoutineUiExpanded() = intent {
        val currentSubRoutineUiExpanded = state.subRoutineUiExpanded
        reduce {
            state.copy(
                subRoutineUiExpanded = !currentSubRoutineUiExpanded,
            )
        }
    }

    fun toggleRepeatDaysUiExpanded() = intent {
        val currentRepeatDaysUiExpanded = state.repeatDaysUiExpanded
        reduce {
            state.copy(
                repeatDaysUiExpanded = !currentRepeatDaysUiExpanded,
            )
        }
    }

    fun togglePeriodUiExpanded() = intent {
        val currentPeriodUiExpanded = state.periodUiExpanded
        reduce {
            state.copy(
                periodUiExpanded = !currentPeriodUiExpanded,
            )
        }
    }

    fun toggleStartTimeUiExpanded() = intent {
        val currentStartTimeUiExpanded = state.startTimeUiExpanded
        reduce {
            state.copy(
                startTimeUiExpanded = !currentStartTimeUiExpanded,
            )
        }
    }

    fun registerRoutine() = intent {
        val currentState = state

        if (currentState.loading) return@intent

        val startTime = currentState.startTime ?: return@intent

        val repeatDay = when (currentState.repeatType) {
            RepeatType.DAILY -> listOf(
                RepeatDay.MONDAY,
                RepeatDay.TUESDAY,
                RepeatDay.WEDNESDAY,
                RepeatDay.THURSDAY,
                RepeatDay.FRIDAY,
                RepeatDay.SATURDAY,
                RepeatDay.SUNDAY,
            )

            RepeatType.DAY ->
                currentState.repeatDays
                    .filter { it.selected }
                    .map { it.day.toRepeatDay() }

            null -> listOf()
        }

        when (val writeRoutineType = currentState.writeRoutineType) {
            WriteRoutineType.Add -> {
                reduce {
                    state.copy(
                        loading = true,
                    )
                }

                val subRoutines = if (currentState.selectNotUseSubRoutines) emptyList() else currentState.subRoutineNames.filter { it.isNotEmpty() }
                val noRepeatRoutine = repeatDay.isEmpty()

                val registerRoutineResult = registerRoutineUseCase(
                    name = currentState.routineName,
                    repeatDay = repeatDay,
                    startTime = startTime.toLocalTime(),
                    startDate = if (noRepeatRoutine) Date.now().toLocalDate() else currentState.startDate.toLocalDate(),
                    endDate = if (noRepeatRoutine) Date.now().toLocalDate() else currentState.endDate.toLocalDate(),
                    subRoutines = subRoutines,
                    recommendedRoutineType = currentState.recommendedRoutineType,
                )

                if (registerRoutineResult.isSuccess) {
                    postSideEffect(WriteRoutineSideEffect.MoveToPreviousScreen)
                } else {
                    reduce {
                        state.copy(
                            loading = false,
                        )
                    }
                }
            }
            is WriteRoutineType.Edit -> {
                val currentRoutineId = routineId ?: return@intent
                val subRoutines = if (currentState.selectNotUseSubRoutines) emptyList() else currentState.subRoutineNames.filter { it.isNotEmpty() }
                val routineUpdateType = if (writeRoutineType.updateRoutineFromNowDate) {
                    RoutineUpdateType.TODAY
                } else {
                    RoutineUpdateType.TOMORROW
                }

                reduce {
                    state.copy(
                        loading = true,
                    )
                }

                val editRoutineResult = editRoutineUseCase(
                    routineId = currentRoutineId,
                    routineUpdateType = routineUpdateType,
                    name = currentState.routineName,
                    repeatDay = repeatDay,
                    startTime = startTime.toLocalTime(),
                    startDate = currentState.startDate.toLocalDate(),
                    endDate = currentState.endDate.toLocalDate(),
                    subRoutines = subRoutines,
                )

                if (editRoutineResult.isSuccess) {
                    postSideEffect(WriteRoutineSideEffect.MoveToPreviousScreen)
                    postSideEffect(WriteRoutineSideEffect.ShowToast("루틴 수정이 완료되었습니다."))
                } else {
                    reduce {
                        state.copy(
                            loading = false,
                        )
                    }
                }
            }
        }
    }
}
