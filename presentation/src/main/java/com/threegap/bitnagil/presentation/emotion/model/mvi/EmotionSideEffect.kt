package com.threegap.bitnagil.presentation.emotion.model.mvi

sealed class EmotionSideEffect {
    data object NavigateToBack : EmotionSideEffect()
    data class ShowToast(val message: String) : EmotionSideEffect()
}
