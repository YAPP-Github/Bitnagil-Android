package com.threegap.bitnagil.presentation.emotion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionsUseCase
import com.threegap.bitnagil.domain.emotion.usecase.RegisterEmotionUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.RegisterRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionIntent
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionSideEffect
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class EmotionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEmotionsUseCase: GetEmotionsUseCase,
    private val registerEmotionUseCase: RegisterEmotionUseCase,
    private val registerRecommendOnBoardingRoutinesUseCase: RegisterRecommendOnBoardingRoutinesUseCase,
) : MviViewModel<EmotionState, EmotionSideEffect, EmotionIntent>(
    savedStateHandle = savedStateHandle,
    initState = EmotionState.Init,
) {
    init {
        loadEmotions()
    }

    private fun loadEmotions() {
        viewModelScope.launch {
            getEmotionsUseCase().fold(
                onSuccess = { emotions ->
                    sendIntent(
                        EmotionIntent.EmotionListLoadSuccess(emotions = emotions.map { Emotion.fromDomain(it) }),
                    )
                },
                onFailure = {
                    // todo 실패 케이스 정의되면 처리
                },
            )
        }
    }

    override suspend fun SimpleSyntax<EmotionState, EmotionSideEffect>.reduceState(intent: EmotionIntent, state: EmotionState): EmotionState? {
        when (intent) {
            is EmotionIntent.EmotionListLoadSuccess -> {
                return state.copy(
                    emotions = intent.emotions,
                    isLoading = false,
                )
            }
            is EmotionIntent.RegisterEmotionSuccess -> {
                return state.copy(
                    recommendRoutines = intent.recommendRoutines,
                    step = EmotionScreenStep.RecommendRoutines,
                    isLoading = false,
                )
            }
            EmotionIntent.RegisterEmotionLoading -> {
                return state.copy(
                    isLoading = true,
                )
            }
            EmotionIntent.RegisterRecommendRoutinesLoading -> {
                return state.copy(
                    isLoading = true,
                )
            }
            EmotionIntent.RegisterRecommendRoutinesFailure -> {
                return state.copy(
                    isLoading = false,
                )
            }
            EmotionIntent.RegisterRecommendRoutinesSuccess -> {
                sendSideEffect(EmotionSideEffect.NavigateToBack)
                return null
            }
            EmotionIntent.BackToSelectEmotionStep -> {
                return state.copy(
                    recommendRoutines = listOf(),
                    step = EmotionScreenStep.Emotion,
                    isLoading = false,
                )
            }

            is EmotionIntent.SelectRecommendRoutine -> {
                val selectChangedRecommendRoutines = state.recommendRoutines.map {
                    if (it.id == intent.recommendRoutineId) {
                        it.copy(selected = !it.selected)
                    } else {
                        it
                    }
                }
                return state.copy(recommendRoutines = selectChangedRecommendRoutines)
            }

            EmotionIntent.NavigateToBack -> {
                sendSideEffect(EmotionSideEffect.NavigateToBack)
                return null
            }
        }
    }

    fun selectEmotion(emotion: Emotion) {
        val isLoading = stateFlow.value.isLoading
        if (isLoading) return

        viewModelScope.launch {
            sendIntent(EmotionIntent.RegisterEmotionLoading)
            registerEmotionUseCase(emotion = emotion.toDomain()).fold(
                onSuccess = { emotionRecommendRoutines ->
                    val recommendRoutines = emotionRecommendRoutines.map { EmotionRecommendRoutineUiModel.fromEmotionRecommendRoutine(it) }
                    sendIntent(EmotionIntent.RegisterEmotionSuccess(recommendRoutines))
                },
                onFailure = {
                    // todo 실패 케이스 정의되면 처리
                },
            )
        }
    }

    fun selectRecommendRoutine(recommendRoutineId: String) {
        viewModelScope.launch {
            sendIntent(EmotionIntent.SelectRecommendRoutine(recommendRoutineId))
        }
    }

    fun moveToPrev() {
        viewModelScope.launch {
            val currentState = stateFlow.value

            when (currentState.step) {
                EmotionScreenStep.Emotion -> sendIntent(EmotionIntent.NavigateToBack)
                EmotionScreenStep.RecommendRoutines -> sendIntent(EmotionIntent.BackToSelectEmotionStep)
            }
        }
    }

    fun registerRecommendRoutines() {
        val isLoading = stateFlow.value.isLoading
        if (isLoading) return

        viewModelScope.launch {
            sendIntent(EmotionIntent.RegisterRecommendRoutinesLoading)

            val currentState = stateFlow.value
            val selectedRecommendRoutineIds = currentState.recommendRoutines.filter { it.selected }.map { it.id }
            registerRecommendOnBoardingRoutinesUseCase(selectedRecommendRoutineIds).fold(
                onSuccess = {
                    sendIntent(EmotionIntent.RegisterRecommendRoutinesSuccess)
                },
                onFailure = {
                    sendIntent(EmotionIntent.RegisterRecommendRoutinesFailure)
                },
            )
        }
    }
}
