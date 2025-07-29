package com.threegap.bitnagil.presentation.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.routine.model.RoutineCompletion
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineByDayUseCase
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.domain.routine.usecase.RoutineCompletionUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.home.model.HomeIntent
import com.threegap.bitnagil.presentation.home.model.HomeSideEffect
import com.threegap.bitnagil.presentation.home.model.HomeState
import com.threegap.bitnagil.presentation.home.model.RoutineSortType
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.RoutinesUiModel
import com.threegap.bitnagil.presentation.home.model.toRoutineByDayDeletion
import com.threegap.bitnagil.presentation.home.model.toUiModel
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
    private val routineCompletionUseCase: RoutineCompletionUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteRoutineByDayUseCase: DeleteRoutineByDayUseCase,
) : MviViewModel<HomeState, HomeSideEffect, HomeIntent>(
    initState = HomeState(),
    savedStateHandle = savedStateHandle,
) {
    private val pendingChangesByDate = mutableMapOf<String, MutableList<RoutineCompletionInfo>>()
    private val backupStatesByDate = mutableMapOf<String, RoutinesUiModel>()
    private val routineSyncTrigger = MutableSharedFlow<LocalDate>()

    init {
        observeWeekChanges()
        observeRoutineUpdates()
        fetchWeeklyRoutines(container.stateFlow.value.currentWeeks)
    }

    override suspend fun SimpleSyntax<HomeState, HomeSideEffect>.reduceState(
        intent: HomeIntent,
        state: HomeState,
    ): HomeState? {
        val newState = when (intent) {
            is HomeIntent.UpdateLoading -> {
                state.copy(isLoading = intent.isLoading)
            }

            is HomeIntent.LoadWeeklyRoutines -> {
                state.copy(routines = intent.routines)
            }

            is HomeIntent.OnDateSelect -> {
                state.copy(selectedDate = intent.date)
            }

            is HomeIntent.OnNextWeekClick -> {
                val newWeek = state.selectedDate.plusWeeks(1).getCurrentWeekDays()
                state.copy(
                    currentWeeks = newWeek,
                    selectedDate = newWeek.first(),
                )
            }

            is HomeIntent.OnPreviousWeekClick -> {
                val newWeek = state.selectedDate.minusWeeks(1).getCurrentWeekDays()
                state.copy(
                    currentWeeks = newWeek,
                    selectedDate = newWeek.first(),
                )
            }

            is HomeIntent.OnRoutineCompletionToggle -> {
                updateMainRoutine(state, intent.routineId, intent.isCompleted)
            }

            is HomeIntent.OnSubRoutineCompletionToggle -> {
                updateSubRoutine(state, intent.routineId, intent.subRoutineId, intent.isCompleted)
            }

            is HomeIntent.OnSortTypeChange -> {
                val newSortType = if (state.currentSortType == intent.sortType) {
                    RoutineSortType.TIME_ORDER
                } else {
                    intent.sortType
                }
                state.copy(
                    currentSortType = newSortType,
                )
            }

            is HomeIntent.DeleteRoutineOptimistically -> {
                val updatedRoutinesByDate = state.routines.routinesByDate.mapValues { (_, routineList) ->
                    routineList.filterNot { it.routineId == intent.routineId }
                }

                state.copy(
                    routines = RoutinesUiModel(routinesByDate = updatedRoutinesByDate),
                    showDeleteConfirmDialog = false,
                    deletingRoutine = null,
                )
            }

            is HomeIntent.RestoreRoutinesAfterDeleteFailure -> {
                state.copy(routines = intent.backupRoutines)
            }

            is HomeIntent.ConfirmRoutineDeletion -> null

            is HomeIntent.ShowRoutineSortBottomSheet -> {
                state.copy(routineSortBottomSheetVisible = true)
            }

            is HomeIntent.HideRoutineSortBottomSheet -> {
                state.copy(routineSortBottomSheetVisible = false)
            }

            is HomeIntent.ShowRoutineDetailsBottomSheet -> {
                state.copy(
                    routineDetailsBottomSheetVisible = true,
                    selectedRoutine = intent.routine,
                )
            }

            is HomeIntent.HideRoutineDetailsBottomSheet -> {
                state.copy(
                    routineDetailsBottomSheetVisible = false,
                    selectedRoutine = null,
                )
            }

            is HomeIntent.ShowDeleteConfirmDialog -> {
                state.copy(
                    showDeleteConfirmDialog = true,
                    deletingRoutine = intent.routine,
                )
            }

            is HomeIntent.HideDeleteConfirmDialog -> {
                state.copy(
                    showDeleteConfirmDialog = false,
                    deletingRoutine = null,
                )
            }

            is HomeIntent.DeleteRoutineByDayOptimistically -> {
                val dateKey = intent.performedDate
                val updatedRoutinesByDate = state.routines.routinesByDate.toMutableMap()
                val routinesForDate = updatedRoutinesByDate[dateKey]?.toMutableList()

                if (routinesForDate != null) {
                    updatedRoutinesByDate[dateKey] = routinesForDate.filterNot {
                        it.routineId == intent.routineId
                    }
                }

                state.copy(
                    routines = RoutinesUiModel(routinesByDate = updatedRoutinesByDate),
                    showDeleteConfirmDialog = false,
                    deletingRoutine = null,
                )
            }

            is HomeIntent.RestoreRoutinesAfterDeleteByDayFailure -> {
                state.copy(routines = intent.backupRoutines)
            }

            is HomeIntent.ConfirmRoutineByDayDeletion -> null
        }
        return newState
    }

    @OptIn(FlowPreview::class)
    private fun observeWeekChanges() {
        viewModelScope.launch {
            container.stateFlow
                .map { it.currentWeeks }
                .distinctUntilChanged()
                .drop(1)
                .debounce(500L)
                .collect { newWeeks ->
                    fetchWeeklyRoutines(newWeeks)
                }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeRoutineUpdates() {
        viewModelScope.launch {
            routineSyncTrigger
                .debounce(2000L)
                .collect { date ->
                    syncRoutineChangesForDate(date)
                }
        }
    }

    private fun fetchWeeklyRoutines(currentWeeks: List<LocalDate>) {
        sendIntent(HomeIntent.UpdateLoading(true))
        val startDate = currentWeeks.first().toString()
        val endDate = currentWeeks.last().toString()
        viewModelScope.launch {
            fetchWeeklyRoutinesUseCase(startDate, endDate).fold(
                onSuccess = { routines ->
                    val routinesUiModel = routines.toUiModel()
                    sendIntent(HomeIntent.LoadWeeklyRoutines(routinesUiModel))
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "루틴 가져오기 실패: ${error.message}")
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
            )
        }
    }

    fun toggleRoutineCompletion(routineId: String, isCompleted: Boolean) {
        val originalState = container.stateFlow.value
        sendIntent(HomeIntent.OnRoutineCompletionToggle(routineId, isCompleted))

        val predictedUpdatedState = updateMainRoutine(originalState, routineId, isCompleted)
        processRoutineToggleChanges(originalState, predictedUpdatedState)
    }

    fun toggleSubRoutineCompletion(routineId: String, subRoutineId: String, isCompleted: Boolean) {
        val originalState = container.stateFlow.value
        sendIntent(HomeIntent.OnSubRoutineCompletionToggle(routineId, subRoutineId, isCompleted))

        val predictedUpdatedState = updateSubRoutine(originalState, routineId, subRoutineId, isCompleted)
        processRoutineToggleChanges(originalState, predictedUpdatedState)
    }

    private fun processRoutineToggleChanges(
        originalState: HomeState,
        updatedState: HomeState,
    ) {
        val selectedDate = originalState.selectedDate
        val dateKey = selectedDate.toString()

        if (!backupStatesByDate.containsKey(dateKey)) {
            backupStatesByDate[dateKey] = originalState.routines
        }

        val originalRoutines = backupStatesByDate[dateKey] ?: originalState.routines
        val changes = calculateStateChanges(originalRoutines, updatedState.routines, selectedDate)

        if (changes.isNotEmpty()) {
            pendingChangesByDate[dateKey] = changes.toMutableList()
            viewModelScope.launch {
                routineSyncTrigger.emit(selectedDate)
            }
        } else {
            pendingChangesByDate.remove(dateKey)
        }
    }

    private fun updateMainRoutine(
        state: HomeState,
        routineId: String,
        isCompleted: Boolean,
    ): HomeState {
        return updateRoutinesForDate(state) { routinesForDate ->
            val routineIndex = routinesForDate.indexOfFirst { it.routineId == routineId }
            if (routineIndex == -1) return@updateRoutinesForDate false

            val updatedRoutine = routinesForDate[routineIndex].copy(
                isCompleted = isCompleted,
                subRoutines = routinesForDate[routineIndex].subRoutines.map { subRoutine ->
                    subRoutine.copy(isCompleted = isCompleted)
                },
            )

            routinesForDate[routineIndex] = updatedRoutine
            true
        }
    }

    private fun updateSubRoutine(
        state: HomeState,
        routineId: String,
        subRoutineId: String,
        isCompleted: Boolean,
    ): HomeState {
        return updateRoutinesForDate(state) { routinesForDate ->
            val routineIndex = routinesForDate.indexOfFirst { it.routineId == routineId }
            if (routineIndex == -1) return@updateRoutinesForDate false

            val routine = routinesForDate[routineIndex]
            val updatedSubRoutines = routine.subRoutines.map { subRoutine ->
                if (subRoutine.subRoutineId == subRoutineId) {
                    subRoutine.copy(isCompleted = isCompleted)
                } else {
                    subRoutine
                }
            }

            val routineCompleted = if (isCompleted) updatedSubRoutines.all { it.isCompleted } else false

            val updatedRoutine = routine.copy(
                subRoutines = updatedSubRoutines,
                isCompleted = routineCompleted,
            )

            routinesForDate[routineIndex] = updatedRoutine
            true
        }
    }

    private fun updateRoutinesForDate(
        state: HomeState,
        updateLogic: (MutableList<RoutineUiModel>) -> Boolean,
    ): HomeState {
        val dateKey = state.selectedDate.toString()
        val routinesForDate = state.routines.routinesByDate[dateKey]?.toMutableList() ?: return state

        if (!updateLogic(routinesForDate)) return state

        val updatedRoutinesByDate = state.routines.routinesByDate.toMutableMap()
        updatedRoutinesByDate[dateKey] = routinesForDate

        return state.copy(routines = RoutinesUiModel(updatedRoutinesByDate))
    }

    private fun calculateStateChanges(
        originalRoutines: RoutinesUiModel,
        updatedRoutines: RoutinesUiModel,
        date: LocalDate,
    ): List<RoutineCompletionInfo> {
        val dateKey = date.toString()
        val originalRoutineList = originalRoutines.routinesByDate[dateKey] ?: emptyList()
        val updatedRoutineList = updatedRoutines.routinesByDate[dateKey] ?: emptyList()

        return buildList {
            updatedRoutineList.forEach { updatedRoutine ->
                val originalRoutine = originalRoutineList.find { it.routineId == updatedRoutine.routineId }

                if (originalRoutine?.isCompleted != updatedRoutine.isCompleted) {
                    add(
                        RoutineCompletionInfo(
                            routineType = updatedRoutine.routineType,
                            routineId = updatedRoutine.routineId,
                            historySeq = updatedRoutine.historySeq,
                            isCompleted = updatedRoutine.isCompleted,
                        ),
                    )
                }

                updatedRoutine.subRoutines.forEach { updatedSubRoutine ->
                    val originalSubRoutine = originalRoutine?.subRoutines
                        ?.find { it.subRoutineId == updatedSubRoutine.subRoutineId }

                    if (originalSubRoutine?.isCompleted != updatedSubRoutine.isCompleted) {
                        add(
                            RoutineCompletionInfo(
                                routineType = updatedSubRoutine.routineType,
                                routineId = updatedSubRoutine.subRoutineId,
                                historySeq = updatedSubRoutine.historySeq,
                                isCompleted = updatedSubRoutine.isCompleted,
                            ),
                        )
                    }
                }
            }
        }
    }

    private suspend fun syncRoutineChangesForDate(date: LocalDate) {
        val dateKey = date.toString()
        val unsyncedChanges = pendingChangesByDate[dateKey] ?: return

        if (unsyncedChanges.isEmpty()) return

        val syncRequest = RoutineCompletion(
            performedDate = dateKey,
            routineCompletions = unsyncedChanges.toList(),
        )

        routineCompletionUseCase(syncRequest).fold(
            onSuccess = {
                pendingChangesByDate.remove(dateKey)
                backupStatesByDate.remove(dateKey)
            },
            onFailure = { error ->
                Log.e("HomeViewModel", "루틴 동기화 실패: ${error.message}")
                val backupState = backupStatesByDate[dateKey] ?: return
                sendIntent(HomeIntent.LoadWeeklyRoutines(backupState))
                pendingChangesByDate.remove(dateKey)
                backupStatesByDate.remove(dateKey)
                sendIntent(HomeIntent.UpdateLoading(false))
            },
        )
    }

    fun deleteRoutine(routineId: String) {
        val currentRoutines = container.stateFlow.value.routines
        sendIntent(HomeIntent.DeleteRoutineOptimistically(routineId))

        viewModelScope.launch {
            deleteRoutineUseCase(routineId).fold(
                onSuccess = {
                    sendIntent(HomeIntent.ConfirmRoutineDeletion(routineId))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "루틴 삭제 실패: ${error.message}")
                    sendIntent(HomeIntent.RestoreRoutinesAfterDeleteFailure(currentRoutines))
                },
            )
        }
    }

    fun deleteRoutineByDay(routineUiModel: RoutineUiModel) {
        val currentRoutines = container.stateFlow.value.routines
        val performedDate = container.stateFlow.value.selectedDate.toString()

        sendIntent(
            HomeIntent.DeleteRoutineByDayOptimistically(
                routineId = routineUiModel.routineId,
                performedDate = performedDate,
            ),
        )

        viewModelScope.launch {
            val routineByDayDeletion = routineUiModel.toRoutineByDayDeletion(performedDate)

            deleteRoutineByDayUseCase(routineByDayDeletion).fold(
                onSuccess = {
                    sendIntent(
                        HomeIntent.ConfirmRoutineByDayDeletion(
                            routineId = routineUiModel.routineId,
                            performedDate = performedDate,
                        ),
                    )
                },
                onFailure = {
                    Log.e("HomeViewModel", "루틴 삭제 실패: ${it.message}")
                    sendIntent(HomeIntent.RestoreRoutinesAfterDeleteByDayFailure(currentRoutines))
                },
            )
        }
    }
}
