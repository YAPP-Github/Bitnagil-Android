package com.threegap.bitnagil.data.onboarding.model.dto

data class OnBoardingRecommendRoutineDto(
    val recommendedRoutineId: Int,
    val recommendedRoutineName: String,
    val routineDescription: String,
    val recommendedRoutineDetails: List<OnBoardingRecommendRoutineDetailDto>
)
