package com.threegap.bitnagil.presentation.emotion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionsUseCase
import com.threegap.bitnagil.domain.emotion.usecase.RegisterEmotionUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionIntent
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionSideEffect
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import javax.inject.Inject

@HiltViewModel
class EmotionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEmotionsUseCase: GetEmotionsUseCase,
    private val registerEmotionUseCase: RegisterEmotionUseCase,
) : MviViewModel<EmotionState, EmotionSideEffect, EmotionIntent>(
    savedStateHandle = savedStateHandle,
    initState = EmotionState.Init
) {
    init {
        loadEmotions()
    }

    private fun loadEmotions() {
        viewModelScope.launch {
            getEmotionsUseCase().fold(
                onSuccess = { emotions ->
                    sendIntent(
                        EmotionIntent.EmotionListLoadSuccess(emotions = emotions.map { Emotion.fromDomain(it) })
                    )
                },
                onFailure = {
                    // todo 실패 케이스 정의되면 처리
                },
            )
        }
    }

    override suspend fun SimpleSyntax<EmotionState, EmotionSideEffect>.reduceState(intent: EmotionIntent, state: EmotionState): EmotionState? {
        when(intent) {
            is EmotionIntent.EmotionListLoadSuccess -> {
                return state.copy(
                    emotions = intent.emotions,
                    isLoading = false,
                )
            }
            EmotionIntent.RegisterEmotionSuccess -> {
                postSideEffect(EmotionSideEffect.NavigateToBack)
                return null
            }
        }
    }

    fun selectEmotion(emotion: Emotion) {
        viewModelScope.launch {
            registerEmotionUseCase(emotion = emotion.toDomain()).fold(
                onSuccess = {
                    sendIntent(EmotionIntent.RegisterEmotionSuccess)
                },
                onFailure = {
                    // todo 실패 케이스 정의되면 처리
                }
            )
        }
    }
}
