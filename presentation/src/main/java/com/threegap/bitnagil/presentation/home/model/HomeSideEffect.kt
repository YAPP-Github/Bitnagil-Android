package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface HomeSideEffect : MviSideEffect {
    data object NavigateToGuide : HomeSideEffect
    data object NavigateToRegisterRoutine : HomeSideEffect
    data object NavigateToEmotion : HomeSideEffect
    data class NavigateToRoutineList(val selectedDate: String) : HomeSideEffect
}
