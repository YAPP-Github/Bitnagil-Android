package com.threegap.bitnagil.presentation.emotion.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed class EmotionSideEffect : MviSideEffect {
    data object NavigateToBack : EmotionSideEffect()
    data class ShowToast(val message: String) : EmotionSideEffect()
}
