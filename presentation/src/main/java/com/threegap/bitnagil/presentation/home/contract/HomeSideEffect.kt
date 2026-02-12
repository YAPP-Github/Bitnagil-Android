package com.threegap.bitnagil.presentation.home.contract

sealed interface HomeSideEffect {
    data object NavigateToGuide : HomeSideEffect
    data object NavigateToRegisterRoutine : HomeSideEffect
    data object NavigateToEmotion : HomeSideEffect
    data class NavigateToRoutineList(val selectedDate: String) : HomeSideEffect
}
