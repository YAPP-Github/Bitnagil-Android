package com.threegap.bitnagil.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.emotion.usecase.ObserveDailyEmotionUseCase
import com.threegap.bitnagil.domain.routine.model.ToggleStrategy
import com.threegap.bitnagil.domain.routine.usecase.ApplyRoutineToggleUseCase
import com.threegap.bitnagil.domain.routine.usecase.ObserveWeeklyRoutinesUseCase
import com.threegap.bitnagil.domain.user.usecase.ObserveUserProfileUseCase
import com.threegap.bitnagil.presentation.screen.home.contract.HomeSideEffect
import com.threegap.bitnagil.presentation.screen.home.contract.HomeState
import com.threegap.bitnagil.presentation.screen.home.model.toUiModel
import com.threegap.bitnagil.presentation.screen.home.util.getCurrentWeekDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeWeeklyRoutinesUseCase: ObserveWeeklyRoutinesUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val observeDailyEmotionUseCase: ObserveDailyEmotionUseCase,
    private val applyRoutineToggleUseCase: ApplyRoutineToggleUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> =
        container(
            initialState = HomeState.INIT,
            buildSettings = { repeatOnSubscribedStopTimeout = 5_000L },
            onCreate = {
                observeDailyEmotion()
                observeUserProfile()
                observeWeeklyRoutines()
            },
        )

    private fun observeDailyEmotion() {
        intent {
            repeatOnSubscription {
                observeDailyEmotionUseCase().collect { result ->
                    result.fold(
                        onSuccess = { reduce { state.copy(dailyEmotion = it.toUiModel()) } },
                        onFailure = {},
                    )
                }
            }
        }
    }

    private fun observeUserProfile() {
        intent {
            repeatOnSubscription {
                observeUserProfileUseCase().collect { result ->
                    result.fold(
                        onSuccess = { reduce { state.copy(userNickname = it.nickname) } },
                        onFailure = {},
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeWeeklyRoutines() {
        intent {
            repeatOnSubscription {
                container.stateFlow
                    .map { it.currentWeeks }
                    .distinctUntilChanged()
                    .debounce(300L)
                    .flatMapLatest { weeks ->
                        observeWeeklyRoutinesUseCase(weeks.first().toString(), weeks.last().toString())
                    }
                    .catch { e -> Log.e("HomeViewModel", "루틴 가져오기 실패: ${e.message}") }
                    .collect { reduce { state.copy(routineSchedule = it.toUiModel()) } }
            }
        }
    }

    fun selectDate(date: LocalDate) {
        intent {
            reduce { state.copy(selectedDate = date) }
        }
    }

    fun getNextWeek() {
        intent {
            val newWeek = state.selectedDate.plusWeeks(1).getCurrentWeekDays()
            reduce { state.copy(currentWeeks = newWeek, selectedDate = newWeek.first()) }
        }
    }

    fun getPreviousWeek() {
        intent {
            val newWeek = state.selectedDate.minusWeeks(1).getCurrentWeekDays()
            reduce { state.copy(currentWeeks = newWeek, selectedDate = newWeek.first()) }
        }
    }

    fun toggleRoutine(routineId: String) {
        intent {
            applyToggle(routineId, ToggleStrategy.Main)
        }
    }

    fun toggleSubRoutine(routineId: String, subRoutineIndex: Int) {
        intent {
            applyToggle(routineId, ToggleStrategy.Sub(subRoutineIndex))
        }
    }

    private suspend fun applyToggle(routineId: String, strategy: ToggleStrategy) {
        subIntent {
            val dateKey = state.selectedDate.toString()
            val routine = state.selectedDateRoutines.find { it.id == routineId } ?: return@subIntent

            applyRoutineToggleUseCase(
                dateKey = dateKey,
                routineId = routineId,
                isCompleted = routine.isCompleted,
                subRoutineCompletionStates = routine.subRoutineCompletionStates,
                strategy = strategy,
            )
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
}
