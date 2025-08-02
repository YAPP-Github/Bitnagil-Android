package com.threegap.bitnagil.presentation.emotion.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel

sealed class EmotionIntent : MviIntent {
    data class EmotionListLoadSuccess(val emotions: List<Emotion>) : EmotionIntent()
    data class RegisterEmotionSuccess(val recommendRoutines: List<EmotionRecommendRoutineUiModel>) : EmotionIntent()
    data object RegisterEmotionLoading : EmotionIntent()
    data object RegisterRecommendRoutinesLoading : EmotionIntent()
    data object RegisterRecommendRoutinesSuccess : EmotionIntent()
    data object RegisterRecommendRoutinesFailure : EmotionIntent()
    data object BackToSelectEmotionStep : EmotionIntent()
    data class SelectRecommendRoutine(val recommendRoutineId: String) : EmotionIntent()
    data object NavigateToBack : EmotionIntent()
}
