package com.threegap.bitnagil.presentation.emotion.model.mvi

import androidx.compose.runtime.Immutable
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.EmotionUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class EmotionState(
    val emotionTypeUiModels: List<EmotionUiModel>,
    val isLoading: Boolean,
    val recommendRoutines: List<EmotionRecommendRoutineUiModel>,
    val step: EmotionScreenStep,
) : MviState {
    companion object {
        val Init = EmotionState(
            emotionTypeUiModels = emptyList(),
            isLoading = true,
            recommendRoutines = emptyList(),
            step = EmotionScreenStep.Emotion,
        )
    }

    val registerRecommendRoutinesButtonEnabled: Boolean
        get() = recommendRoutines.any { it.selected }
}
