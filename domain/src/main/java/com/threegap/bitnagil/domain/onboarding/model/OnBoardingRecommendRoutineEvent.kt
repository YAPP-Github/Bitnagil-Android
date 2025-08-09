package com.threegap.bitnagil.domain.onboarding.model

sealed interface OnBoardingRecommendRoutineEvent {
    data class AddRoutines(val routineIds: List<String>) : OnBoardingRecommendRoutineEvent
}
