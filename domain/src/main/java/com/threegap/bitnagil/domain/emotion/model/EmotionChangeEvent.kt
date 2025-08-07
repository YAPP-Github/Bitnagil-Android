package com.threegap.bitnagil.domain.emotion.model

sealed interface EmotionChangeEvent {
    data class ChangeEmotion(val emotionType: String) : EmotionChangeEvent
}
