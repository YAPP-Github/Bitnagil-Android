package com.threegap.bitnagil.presentation.screen.emotion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionsUseCase
import com.threegap.bitnagil.domain.emotion.usecase.RegisterEmotionUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.RegisterRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.presentation.screen.emotion.contract.EmotionSideEffect
import com.threegap.bitnagil.presentation.screen.emotion.contract.EmotionState
import com.threegap.bitnagil.presentation.screen.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.screen.emotion.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class EmotionViewModel @Inject constructor(
    private val getEmotionsUseCase: GetEmotionsUseCase,
    private val registerEmotionUseCase: RegisterEmotionUseCase,
    private val registerRecommendOnBoardingRoutinesUseCase: RegisterRecommendOnBoardingRoutinesUseCase,
    savedStateHandle: SavedStateHandle,
) : ContainerHost<EmotionState, EmotionSideEffect>, ViewModel() {

    override val container: Container<EmotionState, EmotionSideEffect> = container(initialState = EmotionState.Init, savedStateHandle = savedStateHandle)

    init {
        loadEmotions()
    }

    private fun loadEmotions() =
        intent {
            getEmotionsUseCase().fold(
                onSuccess = { emotions ->
                    reduce {
                        state.copy(
                            emotionTypeUiModels = emotions.map { it.toUiModel() },
                            isLoading = false,
                        )
                    }
                },
                onFailure = {
                    // todo 실패 케이스 정의되면 처리
                },
            )
        }

    fun selectEmotion(emotionType: String, minimumDelay: Long = 0) =
        intent {
            val isLoading = state.isLoading
            if (isLoading) return@intent

            reduce {
                state.copy(
                    isLoading = true,
                    showLoadingView = true,
                )
            }

            viewModelScope.launch {
                if (minimumDelay > 0) {
                    delay(minimumDelay)
                }

                registerEmotionUseCase(emotionType = emotionType).fold(
                    onSuccess = { emotionRecommendRoutines ->
                        val recommendRoutines = emotionRecommendRoutines.map { it.toUiModel() }
                        reduce {
                            state.copy(
                                recommendRoutines = recommendRoutines,
                                step = EmotionScreenStep.RecommendRoutines,
                                isLoading = false,
                                showLoadingView = false,
                            )
                        }
                    },
                    onFailure = {
                        postSideEffect(EmotionSideEffect.ShowToast(message = it.message ?: "에러가 발생했습니다. 잠시 후 시도해주세요."))
                        postSideEffect(EmotionSideEffect.NavigateToBack)
                    },
                )
            }
        }

    fun selectRecommendRoutine(recommendRoutineId: String) =
        intent {
            val selectChangedRecommendRoutines = state.recommendRoutines.map {
                if (it.id == recommendRoutineId) {
                    it.copy(selected = !it.selected)
                } else {
                    it
                }
            }
            reduce { state.copy(recommendRoutines = selectChangedRecommendRoutines) }
        }

    fun moveToPrev() =
        intent {
            when (state.step) {
                EmotionScreenStep.Emotion -> postSideEffect(EmotionSideEffect.NavigateToBack)
                EmotionScreenStep.RecommendRoutines -> reduce {
                    state.copy(
                        recommendRoutines = listOf(),
                        step = EmotionScreenStep.Emotion,
                        isLoading = false,
                    )
                }
            }
        }

    fun registerRecommendRoutines() =
        intent {
            val isLoading = state.isLoading
            if (isLoading) return@intent

            viewModelScope.launch {
                reduce { state.copy(isLoading = true) }

                val selectedRecommendRoutineIds = state.recommendRoutines.filter { it.selected }.map { it.id }
                registerRecommendOnBoardingRoutinesUseCase(selectedRecommendRoutineIds).fold(
                    onSuccess = {
                        postSideEffect(EmotionSideEffect.NavigateToBack)
                    },
                    onFailure = {
                        reduce { state.copy(isLoading = false) }
                    },
                )
            }
        }
}
