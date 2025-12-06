package com.threegap.bitnagil.presentation.recommendroutine.model

sealed interface RecommendRoutineSideEffect {
    data object NavigateToEmotion : RecommendRoutineSideEffect
    data class NavigateToRegisterRoutine(val routineId: String) : RecommendRoutineSideEffect
}
