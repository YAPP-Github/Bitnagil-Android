package com.threegap.bitnagil.presentation.emotion.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.emotion.model.Emotion

sealed class EmotionIntent: MviIntent {
    data class EmotionListLoadSuccess(val emotions: List<Emotion>) : EmotionIntent()
    data object RegisterEmotionSuccess : EmotionIntent()
}
