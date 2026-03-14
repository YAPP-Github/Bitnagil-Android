package com.threegap.bitnagil.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.emotion.usecase.FetchDailyEmotionUseCase
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionChangeEventFlowUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingRecommendRoutineEventFlowUseCase
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.domain.routine.usecase.GetWriteRoutineEventFlowUseCase
import com.threegap.bitnagil.domain.routine.usecase.RoutineCompletionUseCase
import com.threegap.bitnagil.domain.routine.usecase.ToggleRoutineUseCase
import com.threegap.bitnagil.domain.user.usecase.ObserveUserProfileUseCase
import com.threegap.bitnagil.presentation.screen.home.contract.HomeSideEffect
import com.threegap.bitnagil.presentation.screen.home.contract.HomeState
import com.threegap.bitnagil.presentation.screen.home.model.ToggleStrategy
import com.threegap.bitnagil.presentation.screen.home.model.toUiModel
import com.threegap.bitnagil.presentation.screen.home.util.getCurrentWeekDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val fetchDailyEmotionUseCase: FetchDailyEmotionUseCase,
    private val routineCompletionUseCase: RoutineCompletionUseCase,
    private val getWriteRoutineEventFlowUseCase: GetWriteRoutineEventFlowUseCase,
    private val getEmotionChangeEventFlowUseCase: GetEmotionChangeEventFlowUseCase,
    private val getOnBoardingRecommendRoutineEventFlowUseCase: GetOnBoardingRecommendRoutineEventFlowUseCase,
    private val toggleRoutineUseCase: ToggleRoutineUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> = container(initialState = HomeState.INIT)

    private val pendingChangesByDate = mutableMapOf<String, MutableMap<String, RoutineCompletionInfo>>()
    private val routineSyncTrigger = MutableSharedFlow<String>(extraBufferCapacity = 64)

    init {
        initialize()
    }

    fun selectDate(data: LocalDate) {
        intent {
            val previousDateKey = state.selectedDate.toString()
            if (pendingChangesByDate.containsKey(previousDateKey)) {
                syncRoutineChangesForDate(previousDateKey)
            }

            reduce { state.copy(selectedDate = data) }
        }
    }

    fun getNextWeek() {
        intent {
            val currentDateKey = state.selectedDate.toString()
            if (pendingChangesByDate.containsKey(currentDateKey)) {
                syncRoutineChangesForDate(currentDateKey)
            }

            val newWeek = state.selectedDate.plusWeeks(1).getCurrentWeekDays()
            reduce { state.copy(currentWeeks = newWeek, selectedDate = newWeek.first()) }
        }
    }

    fun getPreviousWeek() {
        intent {
            val currentDateKey = state.selectedDate.toString()
            if (pendingChangesByDate.containsKey(currentDateKey)) {
                syncRoutineChangesForDate(currentDateKey)
            }

            val newWeek = state.selectedDate.minusWeeks(1).getCurrentWeekDays()
            reduce { state.copy(currentWeeks = newWeek, selectedDate = newWeek.first()) }
        }
    }

    fun toggleRoutine(routineId: String) {
        intent {
            updateRoutineState(routineId, ToggleStrategy.Main)
        }
    }

    fun toggleSubRoutine(routineId: String, subRoutineIndex: Int) {
        intent {
            updateRoutineState(routineId, ToggleStrategy.Sub(subRoutineIndex))
        }
    }

    private suspend fun updateRoutineState(routineId: String, strategy: ToggleStrategy) {
        subIntent {
            val dateKey = state.selectedDate.toString()
            val dailySchedule = state.routineSchedule.dailyRoutines[dateKey] ?: return@subIntent
            val routine = dailySchedule.routines.find { it.id == routineId } ?: return@subIntent

            val toggledState = when (strategy) {
                is ToggleStrategy.Main -> {
                    toggleRoutineUseCase.toggleMainRoutine(
                        isCompleted = routine.isCompleted,
                        subRoutineStates = routine.subRoutineCompletionStates,
                    )
                }

                is ToggleStrategy.Sub -> {
                    toggleRoutineUseCase.toggleSubRoutine(
                        index = strategy.index,
                        subRoutineStates = routine.subRoutineCompletionStates,
                    )
                }
            } ?: return@subIntent

            val updatedRoutines = dailySchedule.routines.map { routine ->
                if (routine.id == routineId) {
                    routine.copy(
                        isCompleted = toggledState.isCompleted,
                        subRoutineCompletionStates = toggledState.subRoutinesIsCompleted,
                    )
                } else {
                    routine
                }
            }

            val updatedDailySchedule = dailySchedule.copy(
                routines = updatedRoutines,
                isAllCompleted = updatedRoutines.all { it.isCompleted },
            )

            val newSchedule = state.routineSchedule.copy(
                dailyRoutines = state.routineSchedule.dailyRoutines + (dateKey to updatedDailySchedule),
            )

            reduce { state.copy(routineSchedule = newSchedule) }

            val change = RoutineCompletionInfo(
                routineId = routineId,
                routineCompleteYn = toggledState.isCompleted,
                subRoutineCompleteYn = toggledState.subRoutinesIsCompleted,
            )

            val dateChanges = pendingChangesByDate.getOrPut(dateKey) { mutableMapOf() }
            dateChanges[routineId] = change

            routineSyncTrigger.emit(dateKey)
        }
    }

    fun navigateToGuide() {
        intent {
            postSideEffect(HomeSideEffect.NavigateToGuide)
        }
    }

    fun navigateToEmotion() {
        intent {
            postSideEffect(HomeSideEffect.NavigateToEmotion)
        }
    }

    fun navigateToRegisterRoutine() {
        intent {
            postSideEffect(HomeSideEffect.NavigateToRegisterRoutine)
        }
    }

    fun navigateToRoutineList() {
        intent {
            val selectedDate = state.selectedDate.toString()
            postSideEffect(HomeSideEffect.NavigateToRoutineList(selectedDate))
        }
    }

    private fun initialize() {
        intent {
            coroutineScope {
                launch { observeUserProfile() }
                launch { fetchDailyEmotion() }
                launch { fetchWeeklyRoutines(state.currentWeeks) }
                launch { observeWriteRoutineEvent() }
                launch { observeEmotionChangeEvent() }
                launch { observeRecommendRoutineEvent() }
                launch { observeWeekChanges() }
                launch { observeRoutineUpdates() }
            }
        }
    }

    private suspend fun observeWriteRoutineEvent() {
        subIntent {
            getWriteRoutineEventFlowUseCase().collect {
                fetchWeeklyRoutines(state.currentWeeks)
            }
        }
    }

    private suspend fun observeEmotionChangeEvent() {
        subIntent {
            getEmotionChangeEventFlowUseCase().collect {
                fetchDailyEmotion()
            }
        }
    }

    private suspend fun observeRecommendRoutineEvent() {
        subIntent {
            getOnBoardingRecommendRoutineEventFlowUseCase().collect {
                fetchWeeklyRoutines(state.currentWeeks)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private suspend fun observeWeekChanges() {
        subIntent {
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
    private suspend fun observeRoutineUpdates() {
        subIntent {
            routineSyncTrigger
                .debounce(500L)
                .collect { dateKey ->
                    syncRoutineChangesForDate(dateKey)
                }
        }
    }

    private fun observeUserProfile() {
        intent {
            repeatOnSubscription {
                reduce { state.copy(loadingCount = state.loadingCount + 1) }
                observeUserProfileUseCase().collect { result ->
                    result.fold(
                        onSuccess = {
                            reduce { state.copy(userNickname = it.nickname, loadingCount = state.loadingCount - 1) }
                        },
                        onFailure = {
                            Log.e("HomeViewModel", "유저 정보 가져오기 실패: ${it.message}")
                            reduce { state.copy(loadingCount = state.loadingCount - 1) }
                        },
                    )
                }
            }
        }
    }

    private suspend fun fetchWeeklyRoutines(currentWeeks: List<LocalDate>) {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }
            val startDate = currentWeeks.first().toString()
            val endDate = currentWeeks.last().toString()
            fetchWeeklyRoutinesUseCase(startDate, endDate).fold(
                onSuccess = {
                    reduce { state.copy(routineSchedule = it.toUiModel(), loadingCount = state.loadingCount - 1) }
                },
                onFailure = {
                    Log.e("HomeViewModel", "루틴 가져오기 실패: ${it.message}")
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }

    private suspend fun fetchDailyEmotion() {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }
            fetchDailyEmotionUseCase().fold(
                onSuccess = {
                    reduce { state.copy(dailyEmotion = it.toUiModel(), loadingCount = state.loadingCount - 1) }
                },
                onFailure = {
                    Log.e("HomeViewModel", "나의 감정 실패: ${it.message}")
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }

    private fun syncRoutineChangesForDate(dateKey: String) {
        intent {
            val dateChanges = pendingChangesByDate.remove(dateKey)
            if (dateChanges.isNullOrEmpty()) return@intent

            val changesToSync = dateChanges.values.toList()
            val syncRequest = RoutineCompletionInfos(routineCompletionInfos = changesToSync)

            routineCompletionUseCase(syncRequest).fold(
                onSuccess = {},
                onFailure = { error ->
                    fetchWeeklyRoutines(state.currentWeeks)
                },
            )
        }
    }
}
