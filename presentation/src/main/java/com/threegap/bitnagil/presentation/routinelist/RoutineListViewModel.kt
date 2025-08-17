package com.threegap.bitnagil.presentation.routinelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineForDayUseCase
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListIntent
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListSideEffect
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListState
import com.threegap.bitnagil.presentation.routinelist.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteRoutineForDayUseCase: DeleteRoutineForDayUseCase,
) : MviViewModel<RoutineListState, RoutineListSideEffect, RoutineListIntent>(
    savedStateHandle = savedStateHandle,
    initState = RoutineListState(
        selectedDate = savedStateHandle.get<String>("selectedDate")
            ?.takeIf { it.isNotBlank() }
            ?.let { dateString ->
                runCatching { LocalDate.parse(dateString) }.getOrNull()
            }
            ?: LocalDate.now(),
    ),
) {

    init {
        fetchRoutines()
    }

    override suspend fun SimpleSyntax<RoutineListState, RoutineListSideEffect>.reduceState(
        intent: RoutineListIntent,
        state: RoutineListState,
    ): RoutineListState? {
        val newState = when (intent) {
            is RoutineListIntent.UpdateLoading -> state.copy(isLoading = intent.isLoading)
            is RoutineListIntent.LoadRoutines -> state.copy(routines = intent.routines)
            is RoutineListIntent.OnDateSelect -> state.copy(selectedDate = intent.date)

            is RoutineListIntent.ShowDeleteConfirmBottomSheet -> {
                state.copy(
                    selectedRoutine = intent.routine,
                    deleteConfirmBottomSheetVisible = true,
                )
            }

            is RoutineListIntent.HideDeleteConfirmBottomSheet -> state.copy(deleteConfirmBottomSheetVisible = false)
            is RoutineListIntent.ShowEditConfirmBottomSheet -> state.copy(editConfirmBottomSheetVisible = true)
            is RoutineListIntent.HideEditConfirmBottomSheet -> state.copy(editConfirmBottomSheetVisible = false)

            is RoutineListIntent.NavigateToBack -> {
                sendSideEffect(RoutineListSideEffect.NavigateToBack)
                null
            }
        }

        return newState
    }

    private fun fetchRoutines() {
        sendIntent(RoutineListIntent.UpdateLoading(true))
        val currentWeek = container.stateFlow.value.selectedDate.getCurrentWeekDays()
        val startDate = currentWeek.first().toString()
        val endDate = currentWeek.last().toString()
        viewModelScope.launch {
            fetchWeeklyRoutinesUseCase(startDate, endDate).fold(
                onSuccess = { routines ->
                    sendIntent(RoutineListIntent.LoadRoutines(routines.toUiModel()))
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 가져오기 실패: ${it.message}")
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
            )
        }
    }

    fun deleteRoutineCompletely() {
        sendIntent(RoutineListIntent.UpdateLoading(true))
        val selectedRoutine = stateFlow.value.selectedRoutine!!
        viewModelScope.launch {
            deleteRoutineUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    sendIntent(RoutineListIntent.HideDeleteConfirmBottomSheet)
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 삭제 실패: ${it.message}")
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
            )
        }
    }

    fun deleteRoutineForToday() {
        sendIntent(RoutineListIntent.UpdateLoading(true))
        val selectedRoutine = stateFlow.value.selectedRoutine!!
        viewModelScope.launch {
            deleteRoutineForDayUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    sendIntent(RoutineListIntent.HideDeleteConfirmBottomSheet)
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 삭제 실패: ${it.message}")
                    sendIntent(RoutineListIntent.UpdateLoading(false))
                },
            )
        }
    }
}
