package com.threegap.bitnagil.presentation.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.FetchTodayEmotionUseCase
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionChangeEventFlowUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingRecommendRoutineEventFlowUseCase
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.domain.routine.usecase.RoutineCompletionUseCase
import com.threegap.bitnagil.domain.user.usecase.FetchUserProfileUseCase
import com.threegap.bitnagil.domain.writeroutine.usecase.GetWriteRoutineEventFlowUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.home.model.HomeIntent
import com.threegap.bitnagil.presentation.home.model.HomeSideEffect
import com.threegap.bitnagil.presentation.home.model.HomeState
import com.threegap.bitnagil.presentation.home.model.RoutineScheduleUiModel
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
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
import org.orbitmvi.orbit.syntax.Syntax
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val fetchTodayEmotionUseCase: FetchTodayEmotionUseCase,
    private val routineCompletionUseCase: RoutineCompletionUseCase,
    private val getWriteRoutineEventFlowUseCase: GetWriteRoutineEventFlowUseCase,
    private val getEmotionChangeEventFlowUseCase: GetEmotionChangeEventFlowUseCase,
    private val getOnBoardingRecommendRoutineEventFlowUseCase: GetOnBoardingRecommendRoutineEventFlowUseCase,
) : MviViewModel<HomeState, HomeSideEffect, HomeIntent>(
    initState = HomeState(),
    savedStateHandle = savedStateHandle,
) {
    private val pendingChangesByDate = mutableMapOf<String, MutableList<RoutineCompletionInfo>>()
    private val backupStatesByDate = mutableMapOf<String, RoutineScheduleUiModel>()
    private val routineSyncTrigger = MutableSharedFlow<LocalDate>()

    init {
        observeWriteRoutineEvent()
        observeEmotionChangeEvent()
        observeRecommendRoutineEvent()
        observeWeekChanges()
        observeRoutineUpdates()
        fetchWeeklyRoutines(stateFlow.value.currentWeeks)
        fetchUserProfile()
        fetchTodayEmotion(LocalDate.now())
    }

    override suspend fun Syntax<HomeState, HomeSideEffect>.reduceState(
        intent: HomeIntent,
        state: HomeState,
    ): HomeState? {
        val newState = when (intent) {
            is HomeIntent.UpdateLoading -> {
                state.copy(isLoading = intent.isLoading)
            }

            is HomeIntent.LoadUserProfile -> {
                state.copy(userNickname = intent.nickname)
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
                updateSubRoutine(state, intent.routineId, intent.subRoutineIndex, intent.isCompleted)
            }

            is HomeIntent.LoadTodayEmotion -> {
                state.copy(todayEmotion = intent.emotion)
            }

            is HomeIntent.OnHelpClick -> {
                sendSideEffect(HomeSideEffect.NavigateToGuide)
                null
            }

            is HomeIntent.OnRegisterEmotionClick -> {
                sendSideEffect(HomeSideEffect.NavigateToEmotion)
                null
            }

            is HomeIntent.OnRegisterRoutineClick -> {
                sendSideEffect(HomeSideEffect.NavigateToRegisterRoutine)
                null
            }

            is HomeIntent.OnShowMoreRoutinesClick -> {
                val selectedDate = stateFlow.value.selectedDate.toString()
                sendSideEffect(HomeSideEffect.NavigateToRoutineList(selectedDate))
                null
            }

            is HomeIntent.RoutineToggleCompletionFailure -> {
                null
            }
        }
        return newState
    }

    private fun observeWriteRoutineEvent() {
        viewModelScope.launch {
            getWriteRoutineEventFlowUseCase().collect {
                fetchWeeklyRoutines(stateFlow.value.currentWeeks)
            }
        }
    }

    private fun observeEmotionChangeEvent() {
        viewModelScope.launch {
            getEmotionChangeEventFlowUseCase().collect {
                val currentDate = LocalDate.now()
                fetchTodayEmotion(currentDate)
            }
        }
    }

    private fun observeRecommendRoutineEvent() {
        viewModelScope.launch {
            getOnBoardingRecommendRoutineEventFlowUseCase().collect {
                fetchWeeklyRoutines(stateFlow.value.currentWeeks)
            }
        }
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

    private fun fetchUserProfile() {
        sendIntent(HomeIntent.UpdateLoading(true))
        viewModelScope.launch {
            fetchUserProfileUseCase().fold(
                onSuccess = {
                    sendIntent(HomeIntent.LoadUserProfile(it.nickname))
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "유저 정보 가져오기 실패: ${error.message}")
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
            )
        }
    }

    private fun fetchWeeklyRoutines(currentWeeks: List<LocalDate>) {
        sendIntent(HomeIntent.UpdateLoading(true))
        val startDate = currentWeeks.first().toString()
        val endDate = currentWeeks.last().toString()
        viewModelScope.launch {
            fetchWeeklyRoutinesUseCase(startDate, endDate).fold(
                onSuccess = { routines ->
                    sendIntent(HomeIntent.LoadWeeklyRoutines(routines.toUiModel()))
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "루틴 가져오기 실패: ${error.message}")
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
            )
        }
    }

    private fun fetchTodayEmotion(currentDate: LocalDate) {
        sendIntent(HomeIntent.UpdateLoading(true))
        viewModelScope.launch {
            fetchTodayEmotionUseCase(currentDate.toString()).fold(
                onSuccess = { todayEmotion ->
                    sendIntent(HomeIntent.LoadTodayEmotion(todayEmotion?.toUiModel()))
                    sendIntent(HomeIntent.UpdateLoading(false))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "나의 감정 실패: ${error.message}")
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

    fun toggleSubRoutineCompletion(routineId: String, subRoutineIndex: Int, isCompleted: Boolean) {
        val originalState = container.stateFlow.value
        sendIntent(HomeIntent.OnSubRoutineCompletionToggle(routineId, subRoutineIndex, isCompleted))

        val predictedUpdatedState = updateSubRoutine(originalState, routineId, subRoutineIndex, isCompleted)
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
            val routineIndex = routinesForDate.indexOfFirst { it.id == routineId }
            if (routineIndex == -1) return@updateRoutinesForDate false

            val routine = routinesForDate[routineIndex]
            val updatedRoutine = routine.copy(
                isCompleted = isCompleted,
                subRoutineIsCompleted = routine.subRoutineIsCompleted.map { isCompleted },
            )

            routinesForDate[routineIndex] = updatedRoutine
            true
        }
    }

    private fun updateSubRoutine(
        state: HomeState,
        routineId: String,
        subRoutineIndex: Int,
        isCompleted: Boolean,
    ): HomeState {
        return updateRoutinesForDate(state) { routinesForDate ->
            val routineIndex = routinesForDate.indexOfFirst { it.id == routineId }
            if (routineIndex == -1) return@updateRoutinesForDate false

            val routine = routinesForDate[routineIndex]

            if (subRoutineIndex !in routine.subRoutineIsCompleted.indices) {
                return@updateRoutinesForDate false
            }

            val updatedSubRoutineCompleteYn = routine.subRoutineIsCompleted.toMutableList().also {
                it[subRoutineIndex] = isCompleted
            }

            val routineCompleted = updatedSubRoutineCompleteYn.all { it }

            val updatedRoutine = routine.copy(
                subRoutineIsCompleted = updatedSubRoutineCompleteYn,
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
        val dayRoutines = state.routines.dailyRoutines[dateKey] ?: return state
        val routinesForDate = dayRoutines.routines.toMutableList()

        if (!updateLogic(routinesForDate)) return state

        val allCompleted = routinesForDate.all { it.isCompleted }

        val updatedRoutinesByDate = state.routines.dailyRoutines.toMutableMap()
        updatedRoutinesByDate[dateKey] = dayRoutines.copy(
            routines = routinesForDate,
            isAllCompleted = allCompleted,
        )

        return state.copy(routines = RoutineScheduleUiModel(updatedRoutinesByDate))
    }

    private fun calculateStateChanges(
        originalRoutines: RoutineScheduleUiModel,
        updatedRoutines: RoutineScheduleUiModel,
        date: LocalDate,
    ): List<RoutineCompletionInfo> {
        val dateKey = date.toString()
        val originalRoutineList = originalRoutines.dailyRoutines[dateKey]?.routines ?: emptyList()
        val updatedRoutineList = updatedRoutines.dailyRoutines[dateKey]?.routines ?: emptyList()

        return buildList {
            updatedRoutineList.forEach { updatedRoutine ->
                val originalRoutine = originalRoutineList.find { it.id == updatedRoutine.id }

                val hasMainRoutineChanged = originalRoutine?.isCompleted != updatedRoutine.isCompleted
                val hasSubRoutinesChanged = originalRoutine?.subRoutineIsCompleted != updatedRoutine.subRoutineIsCompleted

                if (hasMainRoutineChanged || hasSubRoutinesChanged) {
                    add(
                        RoutineCompletionInfo(
                            routineId = updatedRoutine.id,
                            routineCompleteYn = updatedRoutine.isCompleted,
                            subRoutineCompleteYn = updatedRoutine.subRoutineIsCompleted,
                        ),
                    )
                }
            }
        }
    }

    private suspend fun syncRoutineChangesForDate(date: LocalDate) {
        val dateKey = date.toString()
        val unsyncedChanges = pendingChangesByDate[dateKey] ?: return

        if (unsyncedChanges.isEmpty()) return

        val syncRequest = RoutineCompletionInfos(
            routineCompletionInfos = unsyncedChanges.toList(),
        )

        routineCompletionUseCase(syncRequest).fold(
            onSuccess = {
                pendingChangesByDate.remove(dateKey)
                backupStatesByDate.remove(dateKey)
            },
            onFailure = { error ->
                Log.e("HomeViewModel", "루틴 동기화 실패: ${error.message}")
                val backupState = backupStatesByDate[dateKey] ?: return
                sendIntent(HomeIntent.RoutineToggleCompletionFailure)
                sendIntent(HomeIntent.LoadWeeklyRoutines(backupState))
                pendingChangesByDate.remove(dateKey)
                backupStatesByDate.remove(dateKey)
                sendIntent(HomeIntent.UpdateLoading(false))
            },
        )
    }
}
