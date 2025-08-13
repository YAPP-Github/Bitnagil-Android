package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface HomeSideEffect : MviSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect
    data class ShowToastWithIcon(val message: String) : HomeSideEffect
    data object NavigateToRegisterRoutine : HomeSideEffect
    data object NavigateToEmotion : HomeSideEffect
}
