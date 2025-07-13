package com.threegap.bitnagil.data.onboarding.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRecommendRoutineDetailDto(
    @SerialName("recommendedRoutineDetailId")
    val recommendedRoutineDetailId: Int,
    @SerialName("recommendedRoutineDetailName")
    val recommendedRoutineDetailName: String,
)
