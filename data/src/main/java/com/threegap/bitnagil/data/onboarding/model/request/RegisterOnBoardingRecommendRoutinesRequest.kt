package com.threegap.bitnagil.data.onboarding.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterOnBoardingRecommendRoutinesRequest(
    @SerialName("recommendedRoutineIds")
    val recommendedRoutineIds: List<Int>,
)
