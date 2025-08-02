package com.threegap.bitnagil.data.onboarding.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRecommendSubRoutineDto(
    @SerialName("recommendedSubRoutineId")
    val recommendedSubRoutineId: Int,
    @SerialName("recommendedSubRoutineName")
    val recommendedSubRoutineName: String,
)
