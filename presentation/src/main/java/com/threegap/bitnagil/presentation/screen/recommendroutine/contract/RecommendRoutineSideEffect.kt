package com.threegap.bitnagil.presentation.screen.recommendroutine.contract

sealed interface RecommendRoutineSideEffect {
    data object NavigateToEmotion : RecommendRoutineSideEffect
    data class NavigateToRegisterRoutine(val routineId: String) : RecommendRoutineSideEffect
}
