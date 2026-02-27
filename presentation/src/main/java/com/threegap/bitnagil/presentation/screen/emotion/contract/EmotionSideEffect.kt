package com.threegap.bitnagil.presentation.screen.emotion.contract

sealed interface EmotionSideEffect {
    data object NavigateToBack : EmotionSideEffect
    data class ShowToast(val message: String) : EmotionSideEffect
}
