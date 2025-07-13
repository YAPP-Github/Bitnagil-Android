package com.threegap.bitnagil.data.onboarding.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRecommendRoutinesRequest(
    @SerialName("timeSlot")
    val timeSlot: String,
    @SerialName("emotionType")
    val emotionType: String,
    @SerialName("realOutingFrequency")
    val realOutingFrequency: String,
    @SerialName("targetOutingFrequency")
    val targetOutingFrequency: String,
)
