package com.threegap.bitnagil.presentation.screen.emotion.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.threegap.bitnagil.presentation.screen.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.screen.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.screen.emotion.model.EmotionUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class EmotionState(
    val emotionTypeUiModels: List<EmotionUiModel>,
    val isLoading: Boolean,
    val recommendRoutines: List<EmotionRecommendRoutineUiModel>,
    val step: EmotionScreenStep,
    val showLoadingView: Boolean,
) : Parcelable {
    companion object {
        val Init = EmotionState(
            emotionTypeUiModels = emptyList(),
            isLoading = true,
            recommendRoutines = emptyList(),
            step = EmotionScreenStep.Emotion,
            showLoadingView = false,
        )
    }

    val registerRecommendRoutinesButtonEnabled: Boolean
        get() = recommendRoutines.any { it.selected }
}
