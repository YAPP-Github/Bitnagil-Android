package com.threegap.bitnagil.data.onboarding.model.response

import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingRecommendRoutineDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetOnBoardingRecommendRoutinesResponse(
    @SerialName("recommendedRoutines")
    val recommendedRoutines: List<OnBoardingRecommendRoutineDto>,
)
