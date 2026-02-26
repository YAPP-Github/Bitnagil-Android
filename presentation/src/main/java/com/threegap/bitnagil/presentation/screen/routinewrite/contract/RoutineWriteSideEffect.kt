package com.threegap.bitnagil.presentation.screen.routinewrite.contract

sealed interface RoutineWriteSideEffect {
    data object MoveToPreviousScreen : RoutineWriteSideEffect
    data class ShowToast(val message: String) : RoutineWriteSideEffect
}
