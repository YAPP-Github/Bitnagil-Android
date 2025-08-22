package com.threegap.bitnagil.data.onboarding.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetOnBoardingRecommendRoutinesRequest(
    @SerialName("timeSlot")
    val timeSlot: String,
    @SerialName("emotionType")
    val emotionType: List<String>,
    @SerialName("realOutingFrequency")
    val realOutingFrequency: String,
    @SerialName("targetOutingFrequency")
    val targetOutingFrequency: String,
)
