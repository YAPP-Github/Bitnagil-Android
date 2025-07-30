package com.threegap.bitnagil.presentation.emotion.model.mvi

import androidx.compose.runtime.Immutable
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class EmotionState(
    val emotions: List<Emotion>,
    val isLoading: Boolean,
    val recommendRoutines: List<EmotionRecommendRoutineUiModel> = emptyList(),
    val step: EmotionScreenStep,
) : MviState {
    companion object {
        val Init = EmotionState(
            emotions = emptyList(),
            isLoading = true,
            step = EmotionScreenStep.Emotion,
        )
    }

    val registerRecommendRoutinesButtonEnabled: Boolean
        get() = recommendRoutines.any { it.selected }
}
